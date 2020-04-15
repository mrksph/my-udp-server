package net.pipeline

import io.netty.channel.Channel
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.SimpleChannelInboundHandler
import io.netty.channel.socket.DatagramPacket
import net.game.GameServer
import net.message.GameMessage
import net.session.GameSession
import java.util.concurrent.atomic.AtomicReference

class MessagesHandler(private var connectionManager: GameServer) : SimpleChannelInboundHandler<GameMessage>() {

    lateinit var channel: Channel
    private val sessionReference: AtomicReference<GameSession> = AtomicReference<GameSession>(null)

    override fun channelActive(ctx: ChannelHandlerContext) {
        channel = ctx.channel()
        val session = connectionManager.newSession(channel)
        check(sessionReference.compareAndSet(null, session)) { "Session may not be set more than once" }
        session.onReady()
    }

    override fun channelRead0(context: ChannelHandlerContext?, packet: GameMessage) {
        val session = sessionReference.get()

//        val content = packet.content()

    }


    override fun userEventTriggered(ctx: ChannelHandlerContext?, evt: Any?) {
        println(evt.toString())
    }


    override fun channelInactive(ctx: ChannelHandlerContext?) = sessionReference.get().onDisconnect()


    override fun channelReadComplete(ctx: ChannelHandlerContext) {
        ctx.flush()
    }

    override fun exceptionCaught(ctx: ChannelHandlerContext, cause: Throwable) {
        cause.printStackTrace()
    }


}