package net.session

import io.netty.channel.Channel
import io.netty.handler.codec.CodecException
import net.MainServer
import net.game.GameServer
import net.handler.GameMessageHandler
import net.message.GameMessage
import net.protocol.BaseProtocol

abstract class BaseSession(val server: MainServer,
                           var protocol: BaseProtocol,
                           val channel: Channel,
                           val gameServer: GameServer) {


    open fun messageReceived(message: GameMessage) {
        handleMessage(message)
    }

    private fun handleMessage(message: GameMessage) {
        val messageClass = message.javaClass
        val messageHandler = this.protocol.getMessageHandler<GameMessage>(messageClass)

        if (messageHandler != null) {
            try {
                messageHandler.handle(this, message)
            } catch (t: Throwable) {
                this.onHandlerThrowable(message, messageHandler, t)
            }
        }

    }

    private fun onHandlerThrowable(message: GameMessage, messageHandler: GameMessageHandler<out BaseSession, GameMessage>, t: Throwable) {
    }

    abstract fun getProcessor()

    abstract fun getProtocol()

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