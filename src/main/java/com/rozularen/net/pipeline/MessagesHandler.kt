package com.rozularen.net.pipeline

import com.rozularen.net.game.GameServer
import com.rozularen.net.message.GameMessage
import com.rozularen.net.session.GameSession
import io.netty.channel.Channel
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.SimpleChannelInboundHandler
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

    override fun channelRead0(context: ChannelHandlerContext?, message: GameMessage) {
        val session = sessionReference.get()
        session.messageReceived(message)
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