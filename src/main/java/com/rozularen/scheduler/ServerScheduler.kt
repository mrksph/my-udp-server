package com.rozularen.scheduler

import com.rozularen.net.MainServer
import com.rozularen.net.session.SessionRegistry
import com.rozularen.util.ThreadMaker
import java.util.*
import java.util.concurrent.*

class ServerScheduler(var server: MainServer,
                      var worlds: WorldScheduler,
                      var sessionRegistry: SessionRegistry,
                      var primaryThread: Thread = Thread.currentThread())
    : Scheduler {

    constructor(server: MainServer, worlds: WorldScheduler) : this(server, worlds, server.sessionRegistry)
    val tickEndRun : () -> Unit
    init {
        tickEndRun = worlds::doTickEnd
    }

    private val inTickTask: Deque<Any> = ConcurrentLinkedDeque()
    val pulseFrequency: Long = 500
    val maxThreads: Int = Runtime.getRuntime().availableProcessors()

    private var executor: ScheduledExecutorService = Executors.newSingleThreadScheduledExecutor(ThreadMaker)
    private val asyncTaskExecutor: ExecutorService = ThreadPoolExecutor(
            0,
            maxThreads,
            60L,
            TimeUnit.SECONDS,
            LinkedBlockingDeque<Runnable>(),
            ThreadMaker
    )

    private var tasks: ConcurrentHashMap<Int, Task> = ConcurrentHashMap()


    fun start() {
        executor.scheduleAtFixedRate({
            try {
                pulse()
            } catch (e: Exception) {
                e.printStackTrace()
                System.err.println("Error while pulsing")
            }
        }, 0L, pulseFrequency, TimeUnit.MILLISECONDS)
    }

    fun stop() {
        cancelAllTasks()
        worlds.stop()
        executor.shutdownNow()
    }

    fun schedule(task: Task): Task {
        tasks[task.taskId] = task
        return task
    }

    fun pulse() {
        primaryThread = Thread.currentThread()
        sessionRegistry.pulse()
        // Run the relevant tasks.
        val it: MutableIterator<Task> = tasks.values.iterator()
        while (it.hasNext()) {
            val task: Task = it.next()
            when (task.shouldExecute()) {
                TaskExecutionState.RUN ->
                    if (task.isSync) {
                        task.run()
                    } else {
                        asyncTaskExecutor.submit(task)
                    }
                TaskExecutionState.STOP ->
                    it.remove()
                else -> {
                    // do nothing
                }
            }
        }

        try {
            val currentTick = worlds.beginTick()
            try {
                asyncTaskExecutor.submit(tickEndRun)
            } catch (ex: RejectedExecutionException) {
                worlds.stop()
                return
            }

            var tickTask: Runnable
            synchronized(worlds) {
                while (!worlds.isTickComplete(currentTick)) {
                    while (inTickTask.poll().also { tickTask = it as Runnable } != null) {
                        tickTask.run()
                    }

                }
            }

        } catch(e: InterruptedException) {
            e.printStackTrace()
        }
        finally {
            System.err.flush()
            System.out.flush()
        }

        println("[scheduler] pulse")
    }

    fun runTask(runnable: Runnable) {

        runTaskLater(runnable, 0)
    }

    private fun runTaskLater(runnable: Runnable, delay: Long) {
        runTaskTimer(runnable, delay,-1)
    }

    private fun runTaskTimer(runnable: Runnable, delay:Long, period: Long) {
        schedule(Task(runnable, delay, period, true))
    }

    fun runTaskAsync(runnable: Runnable) {

    }

    fun scheduleInTickExecution(runnable: Runnable) {
        if (isPrimaryThread() || executor.isShutdown) {
            runnable.run()
        } else {
            synchronized(this) {
                inTickTask.addFirst(runnable)
            }
        }
    }

    fun isPrimaryThread(): Boolean = Thread.currentThread() == primaryThread


    override fun isQueued(taskId: Int): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun isCurrentlyRunning(taskId: Int): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun cancelAllTasks() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun cancelTask(taskId: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}

