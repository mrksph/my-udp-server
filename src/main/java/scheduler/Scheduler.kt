package scheduler

interface Scheduler {

    fun isQueued(taskId: Int): Boolean
    fun isCurrentlyRunning(taskId: Int): Boolean
    fun cancelAllTasks()
    fun cancelTask(taskId: Int)
}