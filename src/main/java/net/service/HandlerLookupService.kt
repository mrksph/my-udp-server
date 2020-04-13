package net.service

import net.message.GameMessage
import net.handler.GameMessageHandler
import net.session.BaseSession
import java.util.*

class HandlerLookupService {
    private val handlers: Map<Class<out GameMessage?>, GameMessageHandler<in BaseSession, *>> = HashMap()

    fun <M : GameMessage?, H : GameMessageHandler<*, in M>?>
            bind(clazz: Class<M>?, handler: H) {
        //
    }

    fun <M : GameMessage?, H : GameMessageHandler<*, in M>?>
            bind(clazz: Class<M>?, handlerClass: Class<H>) {
        bind(clazz, handlerClass.newInstance())
    }

    fun <M : GameMessage> find (messageClass: Class<M>): GameMessageHandler<in BaseSession, M>? {
        return handlers[messageClass] as GameMessageHandler<in BaseSession, M>?
    }

}
