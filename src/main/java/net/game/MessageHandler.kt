package net.game

import io.netty.channel.Channel
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.SimpleChannelInboundHandler
import net.message.Message
import net.session.Session
import java.util.concurrent.atomic.AtomicReference

class MessageHandler(var connectionManager: GameServer) : SimpleChannelInboundHandler<Message>() {

    lateinit var channel: Channel
    private val sessionReference: AtomicReference<Session> = AtomicReference<Session>(null)

    override fun channelActive(ctx: ChannelHandlerContext) {
        channel = ctx.channel()
        val session = connectionManager.newSession(channel)
        check(sessionReference.compareAndSet(null, session)) { "Session may not be set more than once" }
        session.onReady()
    }


    override fun channelRead0(p0: ChannelHandlerContext?, p1: Message) {
//        val content = packet.content()
//
//        val message1 = content.toString(CharsetUtil.UTF_8)
//        context.fireUserEventTriggered("W")
//        println("message1 $message1")
//
//        if (content.readableBytes() < 7) {
//            return
//        }
//
//
//        val type = content.readByte()
//        System.err.println(packet)
//        if ("QOTM?" == packet.content().toString(CharsetUtil.UTF_8)) {
//            counter++
//            println("This is the $counter request")
//            val copiedBuffer = Unpooled.copiedBuffer("QOTM: " + "SS", CharsetUtil.UTF_8)
//            context.write(DatagramPacket(copiedBuffer, packet.sender()))
//
//            if (counter == 11)
//                context.close()
//        }
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