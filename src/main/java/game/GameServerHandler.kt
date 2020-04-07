package game

import io.netty.buffer.Unpooled
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.SimpleChannelInboundHandler
import io.netty.channel.socket.DatagramPacket
import io.netty.util.CharsetUtil

object GameServerHandler : SimpleChannelInboundHandler<DatagramPacket>() {

    override fun channelRead0(context: ChannelHandlerContext, packet: DatagramPacket) {
        System.err.println(packet)
        if("QOTM?" == packet.content().toString(CharsetUtil.UTF_8)){
            val copiedBuffer = Unpooled.copiedBuffer("QOTM: " + "SS", CharsetUtil.UTF_8)
            context.write(DatagramPacket(copiedBuffer, packet.sender()))
        }
    }

    override fun channelReadComplete(ctx: ChannelHandlerContext) {
        ctx.flush()
    }

    override fun exceptionCaught(ctx: ChannelHandlerContext, cause: Throwable) {
        cause.printStackTrace()
    }

}