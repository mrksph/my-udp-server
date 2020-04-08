package scheduler

import net.MainServer
import net.session.SessionRegistry
import java.util.concurrent.*

class ServerScheduler : Scheduler {

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

    private lateinit var sessionRegistry: SessionRegistry
    private lateinit var primaryThread: Thread
    private lateinit var worlds: WorldScheduler
    private lateinit var server: MainServer


    constructor(server: MainServer, worlds: WorldScheduler) {
        ServerScheduler(server, worlds, server.getSessionRegistry())
    }

    constructor(server: MainServer, worlds: WorldScheduler, sessionRegistry: SessionRegistry) {
        this.server = server
        this.worlds = worlds
        this.sessionRegistry = sessionRegistry
        this.primaryThread = Thread.currentThread()
    }

    fun start() {
        executor.scheduleAtFixedRate({
            try {
                pulse()
            } catch (e: Exception) {
                System.err.println("ERROR")
            }
        }, 0L, pulseFrequency, TimeUnit.MILLISECONDS)
    }


    fun stop() {
        cancelAllTasks()
        worlds.stop()
        executor.shutdownNow()

    }

    fun schedule(task: Task): Task {
        tasks.put(task.id, task)
        return task
    }

    fun pulse() {
        primaryThread = Thread.currentThread()
        println("PULSEEE")

    }

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