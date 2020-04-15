package net.session

import entity.location.Location
import entity.meta.PlayerProfile
import entity.player.GamePlayer
import io.netty.channel.Channel
import io.netty.channel.ChannelFuture
import io.netty.handler.codec.CodecException
import net.MainServer
import net.game.GameServer
import net.message.AsyncGameMessage
import net.message.GameMessage
import net.protocol.BaseProtocol
import java.net.InetSocketAddress
import java.util.*
import java.util.concurrent.ConcurrentLinkedDeque

class GameSession(server: MainServer,
                  protocol: BaseProtocol,
                  channel: Channel,
                  gameServer: GameServer) : BaseSession(server, protocol, channel, gameServer) {

    var version: Int = 0
    var virtualHost: InetSocketAddress? = null
    private var player: GamePlayer? = null
    private val messageQueue: Queue<GameMessage> = ConcurrentLinkedDeque<GameMessage>()

    var online: Boolean = false
    var disconnected: Boolean = false

    override fun messageReceived(message: GameMessage) {
        if (message is AsyncGameMessage && message.isAsync()) {
            super.messageReceived(message)
        } else {
            messageQueue.add(message)
        }
    }

    override fun pulse() {
        var message: GameMessage
        while (messageQueue.poll().also { message = it } != null) {
            if (disconnected) { // disconnected, we are just seeing extra messages now
                break
            }
            super.messageReceived(message)
        }

        if (disconnected) {
            gameServer.sessionInactivated(this)

            player?.remove()

            player = null
        }
    }

    fun setPlayer(profile: PlayerProfile) {
        val playerLocation = getInitialLocation()
        player = GamePlayer(this, profile, playerLocation)
        finalizeLogin()

        if (!isActive()) {
            onDisconnect()
        }

        player!!.join(this)
        player!!.world.server.rawPlayers.add(player!!)
        online = true
    }

    private fun getInitialLocation(): Location {
        val world = server.worldEntries[0].world
        return Location(world, 1.0, 1.0, 1.0, 0F, 0F)
    }

    override fun finalizeLogin() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getProcessor() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getProtocol() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun send(message: GameMessage) {

    }

    private fun sendWithFuture(message: GameMessage): ChannelFuture? {
        if (!this.channel.isActive) {
            throw Exception("Trying to send when session is inactive")
        } else {
            return this.channel.writeAndFlush(message).addListener {
                if (it.cause() != null) {
                    this.onOutboundThrowable(it.cause())
                }
            }
        }
    }

    override fun sendAll() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun disconnect(reason: String) {
        System.err.println("${player!!.name} kicked $reason")
        channel.close()
    }

    fun onOutboundThrowable(cause: Throwable) {

        if (cause is CodecException) {
            System.err.println("Error in network output")
        } else {
            disconnect("")
        }
    }


    override fun onReady() {

    }

    override fun onDisconnect() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}