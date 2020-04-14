package net.service

import net.handler.GameMessageHandler
import net.message.GameMessage
import net.session.BaseSession
import java.util.*

class HandlerLookupService {
    private val handlers: MutableMap<Class<in GameMessage>, GameMessageHandler<in BaseSession, in GameMessage>> = HashMap()

    fun <H : GameMessageHandler<in BaseSession, in GameMessage>>
            bind(clazz: Class<GameMessage>, handler: H) {
        this.handlers[clazz] = handler
    }

    fun <H : GameMessageHandler<in BaseSession, in GameMessage>>
            bind(clazz: Class<GameMessage>, handlerClass: Class<H>) {
        bind(clazz, handlerClass.newInstance())
    }

    fun find(messageClass: Class<in GameMessage>): GameMessageHandler<in BaseSession, in GameMessage> {
        return handlers[messageClass] as GameMessageHandler<in BaseSession, in GameMessage>
    }

}
