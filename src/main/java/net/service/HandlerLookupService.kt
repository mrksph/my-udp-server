@file:Suppress("UNCHECKED_CAST")

package net.service

import net.handler.GameMessageHandler
import net.message.GameMessage
import net.session.BaseSession
import java.util.*

class HandlerLookupService {
    private val handlers: MutableMap<Class<in GameMessage>, GameMessageHandler<in BaseSession, in GameMessage>> = HashMap()

    @Throws(InstantiationException::class, IllegalAccessException::class)
    fun bind(messageClass: Class<in GameMessage>, handlerClass: Class<in GameMessageHandler<in BaseSession, in GameMessage>>) {
        this.bind(messageClass, handlerClass.newInstance() as Class<GameMessageHandler<in BaseSession, in GameMessage>>)
    }

    @Throws(InstantiationException::class, IllegalAccessException::class)
    fun  bind(messageClass: Class<in GameMessage>, handler: GameMessageHandler<in BaseSession, in GameMessage>) {
        handlers[messageClass] = handler
    }

    fun find(clazz: Class<in GameMessage>): GameMessageHandler<in BaseSession, in GameMessage>? {
        return handlers[clazz]
    }

    override fun toString(): String {
        return "HandlerLookupService{handlers=$handlers}"
    }
}
