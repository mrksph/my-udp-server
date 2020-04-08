package game

import io.netty.buffer.Unpooled
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.SimpleChannelInboundHandler
import io.netty.channel.socket.DatagramPacket
import io.netty.util.CharsetUtil

class GameServerHandler(connectionManager: GameServer) : SimpleChannelInboundHandler<DatagramPacket>() {
    var counter = 0

    override fun channelRead0(context: ChannelHandlerContext, packet: DatagramPacket) {
        System.err.println(packet)
        if("QOTM?" == packet.content().toString(CharsetUtil.UTF_8)){
            counter++
            println("This is the $counter request")
            val copiedBuffer = Unpooled.copiedBuffer("QOTM: " + "SS", CharsetUtil.UTF_8)
            context.write(DatagramPacket(copiedBuffer, packet.sender()))

            if(counter == 11)
                context.close()
        }
    }

    override fun channelReadComplete(ctx: ChannelHandlerContext) {
        ctx.flush()
    }

    override fun exceptionCaught(ctx: ChannelHandlerContext, cause: Throwable) {
        cause.printStackTrace()
    }

}