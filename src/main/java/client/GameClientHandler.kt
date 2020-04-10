package client

import io.netty.channel.ChannelHandler
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.SimpleChannelInboundHandler
import io.netty.channel.socket.DatagramPacket
import io.netty.util.CharsetUtil

@ChannelHandler.Sharable
object GameClientHandler : SimpleChannelInboundHandler<DatagramPacket>() {

    override fun channelRead0(context: ChannelHandlerContext, packet: DatagramPacket) {
        val response = packet.content().toString(CharsetUtil.UTF_8)
        if (response.startsWith("QOTM: ")) {
            println("Quote of the Moment: " + response.substring(6))
        }
    }

    override fun exceptionCaught(ctx: ChannelHandlerContext, cause: Throwable) {
        cause.printStackTrace()
        ctx.close()
    }
}