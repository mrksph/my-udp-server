package net.session

import io.netty.channel.Channel
import net.MainServer
import net.game.GameServer
import net.message.Message
import protocol.ProtocolProvider

class BasicSession(server: MainServer,
                   protocolProvider: ProtocolProvider,
                   channel: Channel,
                   gameServer: GameServer) : Session{

    override fun messageReceived(message: Message) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun pulse() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getProcessor() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getProtocol() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun send() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun sendAll() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun disconnect() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onReady() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onDisconnect() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}