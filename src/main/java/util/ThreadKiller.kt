package util

import java.util.logging.Level
import kotlin.system.exitProcess

class ThreadKiller : Thread() {

    private val DELAY: Long = 8000

    init {
        name = "ThreadKiller"
        isDaemon = true
    }

    override fun run() {
        super.run()

        try {
            Thread.sleep(DELAY)
        } catch (exception: InterruptedException) {
            exitProcess(1)
        }

        val traces = getAllStackTraces()
        for ((thread, stack) in traces) {
            if (thread.isDaemon || !thread.isAlive || stack.isEmpty()) { // won't keep JVM from exiting
                continue
            }
            // ask nicely to kill them
            thread.interrupt()
            // wait for them to die on their own
            try {
                thread.join(1000)
            } catch (e: InterruptedException) {
                exitProcess(1)

            }
        }

        exitProcess(1)

    }
}
