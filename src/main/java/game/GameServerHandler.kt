package game

import io.netty.channel.ChannelHandlerContext
import io.netty.channel.SimpleChannelInboundHandler
import java.net.DatagramPacket

class GameServerHandler : SimpleChannelInboundHandler<DatagramPacket>() {

    override fun channelRead0(context: ChannelHandlerContext, packet: DatagramPacket) {
        System.err.println(packet)
    }

    override fun channelReadComplete(ctx: ChannelHandlerContext) {
        ctx.flush()
    }

}