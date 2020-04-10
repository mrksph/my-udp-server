package scheduler

import java.util.concurrent.ExecutionException
import java.util.concurrent.FutureTask
import java.util.concurrent.atomic.AtomicInteger

class Task(var task: Runnable,
           private var delay: Long,
           private var period: Long,
           var isSync: Boolean) : FutureTask<Void>(task, null) {

    private var lastExecutionState: TaskExecutionState = TaskExecutionState.WAIT
    private val nextTaskId: AtomicInteger = AtomicInteger()
    val taskId: Int
    private var counter: Long
    private var description: String
    private lateinit var thread: Thread


    init {
        this.taskId = nextTaskId.getAndIncrement()
        this.description = task.toString()
        this.counter = 0
    }

    fun shouldExecute(): TaskExecutionState {
        val execState = shouldExecuteUpdate()
        lastExecutionState = execState
        return execState
    }

    fun shouldExecuteUpdate(): TaskExecutionState {
        if (isDone) {
            return TaskExecutionState.STOP
        }

        counter++
        if (counter >= delay) {
            if (period == -1L || (counter - delay) % period == 0L) {
                return TaskExecutionState.RUN
            }
        }

        return TaskExecutionState.WAIT
    }

    override fun run() {
        thread = Thread.currentThread()
        if (period == -1L) {
            super.run()
        } else {
            runAndReset()
        }
    }

    override fun done() {
        super.done()
        if (isCancelled) {
            return
        }

        try {
            get()
        } catch (exception: ExecutionException) {

        } catch (exception: InterruptedException) {
            //Task is done, we are in done() method
        }
    }

    override fun toString(): String {
        return ("GlowTask{"
                + "id=" + taskId
                + ", sync=" + isSync
                + ": " + description
                + '}')
    }
}