package net.session

import entity.BasicPlayer
import io.netty.channel.Channel
import net.MainServer
import net.game.GameServer
import net.message.Message
import net.protocol.Protocol

class Session(server: MainServer,
              protocol: Protocol,
              channel: Channel,
              gameServer: GameServer) : BaseSession(server, protocol, channel, gameServer) {

    private lateinit var player: BasicPlayer

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

    override fun send(message: Message) {

    }

    override fun sendAll() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun disconnect() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onReady() {

    }

    override fun onDisconnect() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}