package client

//import io.netty.channel.socket.DatagramPacket
import DEFAULT_PORT
import io.netty.channel.nio.NioEventLoopGroup
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress

class GameClient {

    fun connect() {
        val eventLoopGroup = NioEventLoopGroup()
        try {
            println("Game Client is up")

            var loop = true
            var counter = 0

//            val channel = server.bind().channel()
            val localHost = InetAddress.getLocalHost()
            print(localHost)

            val socket = DatagramSocket()
            var buf: ByteArray = "hello".toByteArray()

            val packet = DatagramPacket(buf, buf.size, localHost, DEFAULT_PORT)

            while (loop) {
                if (counter == 15) loop = false
                try {
                    socket.send(packet)
                } catch (e: Throwable) {
                    e.printStackTrace()
                }
                println(String(buf))
                counter++
            }
        } finally {
            println("Disconnecting Game Client...")
            eventLoopGroup.shutdownGracefully()
            println("Game Client disconnected")
        }
    }

}