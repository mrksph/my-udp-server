package scheduler

import java.util.concurrent.ThreadFactory
import java.util.concurrent.atomic.AtomicInteger

interface ThreadFactory {
    companion object {
        private val threadCounter: AtomicInteger = AtomicInteger()
        fun createNewThread(runnable: Runnable) = Thread(runnable, "server-scheduler-$threadCounter")
    }
}