@file:Suppress("UNCHECKED_CAST")

package net.service

import net.handler.GameMessageHandler
import net.message.GameMessage
import net.session.BaseSession
import java.util.*
import kotlin.reflect.KClass

class HandlerLookupService {
    private val handlers: MutableMap<GameMessage, GameMessageHandler<*,*>> = HashMap()

    @Throws(InstantiationException::class, IllegalAccessException::class)
    fun bind(message: GameMessage, handler: GameMessageHandler<*, *>) {
        handlers[message] = handler
    }

    fun find(message: GameMessage): GameMessageHandler<*, *>? {
        return handlers[message]
    }

    override fun toString(): String {
        return "HandlerLookupService{handlers=$handlers}"
    }
}
