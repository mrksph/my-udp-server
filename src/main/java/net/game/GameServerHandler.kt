package net.game

import io.netty.channel.Channel
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.SimpleChannelInboundHandler
import io.netty.channel.socket.DatagramPacket
import net.session.BasicSession
import java.util.concurrent.atomic.AtomicReference

class GameServerHandler(var connectionManager: GameServer) : SimpleChannelInboundHandler<DatagramPacket>() {
    var counter = 0

    lateinit var channel: Channel

    private val sessionReference: AtomicReference<BasicSession> = AtomicReference<BasicSession>(null)

    override fun channelActive(ctx: ChannelHandlerContext) {
        channel = ctx.channel()
        val session = connectionManager.newSession(channel)
        check(sessionReference.compareAndSet(null, session)) { "Session may not be set more than once" }
        session.onReady()
    }

    override fun channelRead0(context: ChannelHandlerContext, packet: DatagramPacket) {
        val message = packet.content()
        if (message.readableBytes() < 7) {
            return
        }

        val type = message.readByte()

//
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

    override fun channelInactive(ctx: ChannelHandlerContext?)
            = sessionReference.get().onDisconnect()


    override fun channelReadComplete(ctx: ChannelHandlerContext) {
        ctx.flush()
    }

    override fun exceptionCaught(ctx: ChannelHandlerContext, cause: Throwable) {
        cause.printStackTrace()
    }

}