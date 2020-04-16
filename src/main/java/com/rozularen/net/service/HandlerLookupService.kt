@file:Suppress("UNCHECKED_CAST")

package com.rozularen.net.service

import com.rozularen.net.handler.GameMessageHandler
import com.rozularen.net.message.GameMessage
import java.util.*

class HandlerLookupService {
    private val handlers: MutableMap<GameMessage, GameMessageHandler<*, *>> = HashMap()

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
