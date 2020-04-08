package scheduler

import java.util.concurrent.ExecutionException
import java.util.concurrent.FutureTask
import java.util.concurrent.atomic.AtomicInteger

class Task(task: Runnable,
           delay: Long,
           period: Long) : FutureTask<Void>(task, null) {

    private var taskId: Int = 0
    private var counter: Long
    private var period: Long
    private var delay: Long
    private var description: String
    private lateinit var thread: Thread
    private val nextTaskId: AtomicInteger = AtomicInteger()


    init {
        this.taskId = nextTaskId.getAndIncrement()
        this.description = task.toString()
        this.delay = delay
        this.period = period
        this.counter = 0
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
}