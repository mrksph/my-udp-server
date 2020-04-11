package scheduler

import net.MainServer
import net.session.SessionRegistry
import util.ThreadMaker
import java.util.*
import java.util.concurrent.*

class ServerScheduler(var server: MainServer,
                      var worlds: WorldScheduler,
                      var sessionRegistry: SessionRegistry,
                      var primaryThread: Thread = Thread.currentThread()) : Scheduler {

    constructor(server: MainServer, worlds: WorldScheduler) : this(server, worlds, server.sessionRegistry)

    private val inTickTask: Deque<Any> = ConcurrentLinkedDeque()
    val pulseFrequency: Long = 50
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
        println("[scheduler] pulse")
    }

    fun scheduleInTickExecution(run: Runnable) {
        if (isPrimaryThread() || executor.isShutdown) {
            run.run()
        } else {
            synchronized(this) {
                inTickTask.addFirst(run)
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

