package sample

import DEFAULT_PORT
import io.netty.bootstrap.Bootstrap
import io.netty.buffer.Unpooled
import io.netty.channel.ChannelOption
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.DatagramPacket
import io.netty.channel.socket.nio.NioDatagramChannel
import io.netty.util.CharsetUtil
import java.net.InetSocketAddress

object GameClient {

    fun connect() {
        val eventLoopGroup = NioEventLoopGroup()
        try {
            val b = Bootstrap()

            b.group(eventLoopGroup)
                    .channel(NioDatagramChannel::class.java)
                    .option(ChannelOption.SO_BROADCAST, true)
                    .handler(GameClientHandler)

            println("Game Client is up")

            var loop = true
            var counter = 0

            val channel = b.bind(0)
                    .sync()
                    .channel()

            while (loop) {

                if (counter == 5) loop = false

                val packet = createDatagram()

                channel.writeAndFlush(packet).sync()

                println(counter);
                counter++

            }
            channel.close();
        } finally {
            println("Disconnecting Game Client...")
            eventLoopGroup.shutdownGracefully()
            println("Game Client disconnected")
        }
    }

    private fun createDatagram(): DatagramPacket {
        val copiedBuffer = Unpooled.copiedBuffer("QOTM?", CharsetUtil.UTF_8)
        val inetSocketAddress = InetSocketAddress("255.255.255.255", DEFAULT_PORT)

        val packet = DatagramPacket(copiedBuffer, inetSocketAddress)
        return packet
    }

    @JvmStatic
    fun main(args: Array<String>) {
        println("Connecting Game Client (Port: ${DEFAULT_PORT})")
        connect()
    }
}