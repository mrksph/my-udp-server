package net.pipeline

import io.netty.channel.ChannelHandlerContext
import io.netty.channel.SimpleChannelInboundHandler
import net.game.GameServer
import net.message.Message
import net.session.Session
import java.util.concurrent.atomic.AtomicReference

class MessageHandler(var connectionManager: GameServer) : SimpleChannelInboundHandler<Message>() {

    private val sessionReference: AtomicReference<Session> = AtomicReference()

    override fun channelActive(ctx: ChannelHandlerContext) {
        val channel = ctx.channel()
        val session = connectionManager.newSession(channel)
        check(sessionReference.compareAndSet(null, session)) { "Session may not be set more than once" }
        session.onReady()
    }

    override fun channelInactive(ctx: ChannelHandlerContext?)
            = sessionReference.get().onDisconnect()

    override fun channelRead0(context: ChannelHandlerContext, message: Message)
            = sessionReference.get().messageReceived(message)


}