package net.pipeline

import io.netty.channel.Channel
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.SimpleChannelInboundHandler
import io.netty.channel.socket.DatagramPacket
import net.game.GameServer
import net.message.GameMessage
import net.session.GameSession
import java.util.concurrent.atomic.AtomicReference

class MessagesHandler(private var connectionManager: GameServer) : SimpleChannelInboundHandler<DatagramPacket>() {

    lateinit var channel: Channel
    private val sessionReference: AtomicReference<GameSession> = AtomicReference<GameSession>(null)

    override fun channelActive(ctx: ChannelHandlerContext) {
        channel = ctx.channel()
        val session = connectionManager.newSession(channel)
        check(sessionReference.compareAndSet(null, session)) { "Session may not be set more than once" }
        session.onReady()
    }

    override fun channelRead0(context: ChannelHandlerContext?, packet: DatagramPacket) {
        val session = sessionReference.get()
        val content = packet.content()


//        session.messageReceived(message)
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