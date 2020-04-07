package sample

import io.netty.bootstrap.Bootstrap
import io.netty.buffer.Unpooled
import io.netty.channel.ChannelOption
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.DatagramPacket
import io.netty.channel.socket.nio.NioDatagramChannel
import io.netty.util.CharsetUtil
import java.net.InetSocketAddress

object GameClient {

    private const val DEFAULT_PORT: Int = 31047

    fun connect() {
        val eventLoopGroup = NioEventLoopGroup()
        try {
            val b = Bootstrap()

            b.group(eventLoopGroup)
                    .channel(NioDatagramChannel::class.java)
                    .option(ChannelOption.SO_BROADCAST, true)
                    .handler(GameClientHandler())

            val channel = b.bind(0)
                    .sync()
                    .channel()

            val copiedBuffer = Unpooled.copiedBuffer("QOTM", CharsetUtil.UTF_8)
            val inetSocketAddress = InetSocketAddress(31047)

            val packet = DatagramPacket(copiedBuffer, inetSocketAddress)

            channel.writeAndFlush(packet).sync()

            if (!channel.closeFuture().await(5000)) {
                System.err.println("Request timed out")
            }

        } finally {
            eventLoopGroup.shutdownGracefully()
        }
    }
}