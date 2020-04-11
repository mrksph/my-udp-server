package net.session

import io.netty.channel.Channel
import net.MainServer
import net.game.GameServer
import net.message.GameMessage
import net.protocol.Protocol

abstract class BaseSession(server: MainServer,
                           protocol: Protocol,
                           channel: Channel,
                           gameServer: GameServer) {

    abstract fun messageReceived(message: GameMessage)

    abstract fun getProcessor()

    abstract fun getProtocol()

    abstract fun send(message: GameMessage)

    abstract fun sendAll()

    abstract fun disconnect()

    abstract fun onReady()

    abstract fun onDisconnect()

    abstract fun pulse()


}