package net.pipeline

import io.netty.channel.ChannelHandlerContext
import io.netty.channel.SimpleChannelInboundHandler
import net.game.GameServer
import net.message.Message
import net.session.BaseSession
import java.util.concurrent.atomic.AtomicReference

class MessageHandler(var connectionManager: GameServer) : SimpleChannelInboundHandler<Message>() {

    private val baseSessionReference: AtomicReference<BaseSession> = AtomicReference()

    override fun channelActive(ctx: ChannelHandlerContext) {
        val channel = ctx.channel()
        val session = connectionManager.newSession(channel)
        check(baseSessionReference.compareAndSet(null, session)) { "Session may not be set more than once" }
        session.onReady()
    }

    override fun channelInactive(ctx: ChannelHandlerContext?) = baseSessionReference.get().onDisconnect()

    override fun channelRead0(context: ChannelHandlerContext, message: Message) = baseSessionReference.get().messageReceived(message)


}