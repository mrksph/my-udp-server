package com.rozularen.net.session

import com.rozularen.net.MainServer
import com.rozularen.net.game.GameServer
import com.rozularen.net.handler.GameMessageHandler
import com.rozularen.net.message.GameMessage
import com.rozularen.net.pipeline.CodecsHandler
import com.rozularen.net.protocol.BaseProtocol
import io.netty.channel.Channel

abstract class BaseSession(val server: MainServer,
                           protocol: BaseProtocol,
                           val channel: Channel,
                           val gameServer: GameServer) {


    lateinit var sessionId: String

    var protocol = protocol
        set(newProtocol) {
            channel.flush()
            val key = "codecs"
            val codecsHandler = CodecsHandler(newProtocol)
            updatePipeline(key, codecsHandler)
            field = newProtocol
        }


    private fun updatePipeline(key: String, codecsHandler: CodecsHandler) {
        channel.pipeline().replace(key, key, codecsHandler)
    }

    open fun messageReceived(message: GameMessage) {
        handleMessage(message)
    }

    private fun handleMessage(message: GameMessage) {
        val messageHandler = this.protocol.getMessageHandler(message)

        if (messageHandler != null) {
            try {
                println("HANDLING ${message.getName()}")
                messageHandler.handle(this, message)
            } catch (t: Throwable) {
                this.onHandlerThrowable(message, messageHandler, t)
            }
        }

    }

    private fun onHandlerThrowable(message: GameMessage, messageHandler: GameMessageHandler<out BaseSession, out GameMessage>?, t: Throwable) {
    }

    abstract fun getProcessor()

    abstract fun send(message: GameMessage)

    abstract fun sendAll()

    abstract fun disconnect(reason: String)

    abstract fun onReady()

    abstract fun onDisconnect()

    abstract fun pulse()
    abstract fun finalizeLogin()

    open fun isActive(): Boolean {
        return channel.isActive
    }


}