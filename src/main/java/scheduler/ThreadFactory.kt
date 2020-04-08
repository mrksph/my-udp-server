package scheduler

import java.util.concurrent.ThreadFactory
import java.util.concurrent.atomic.AtomicInteger

object ThreadMaker : ThreadFactory {

    private val threadCounter: AtomicInteger = AtomicInteger()

    override fun newThread(runnable: Runnable) : Thread {
        return Thread(runnable, "server-scheduler-$threadCounter")
    }
}