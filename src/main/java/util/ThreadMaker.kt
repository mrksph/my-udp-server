package util

import java.util.concurrent.ThreadFactory
import java.util.concurrent.atomic.AtomicInteger

object ThreadMaker : ThreadFactory {
    private val threadCounter = AtomicInteger()
    override fun newThread(runnable: Runnable): Thread {
        return Thread(runnable, "Server-scheduler-" + threadCounter.getAndIncrement())
    }

}