package com.rozularen.event

import com.rozularen.net.ServerProvider
import java.util.concurrent.ExecutionException
import java.util.concurrent.FutureTask


class EventFactory
//TODO: IMPLEMENT THIS CLASS MAÑANA POR LA MAÑANA URGENTE ES MUY NECESARIAA
//TODO: IMPLEMENT THIS CLASS MAÑANA POR LA MAÑANA URGENTE ES MUY NECESARIAA
{

    companion object {
        fun <T : Event> callEvent(event: T): T {
            val server = ServerProvider.server
            if (event.isAsync) {
                return event
            } else {
                val task = FutureTask(Runnable {
                    server
                }, event)

                server.scheduler.scheduleInTickExecution(task)

                return try {
                    task.get()
                } catch (e: InterruptedException) {
                    event
                } catch (e: ExecutionException) {
                    e.printStackTrace()
                    throw RuntimeException(e)
                }

            }
        }
    }
}