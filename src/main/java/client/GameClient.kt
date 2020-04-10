package client

//import io.netty.channel.socket.DatagramPacket
import DEFAULT_PORT
import io.netty.channel.nio.NioEventLoopGroup
import java.io.IOException
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
                } catch (ex: IOException) {
                    ex.printStackTrace()
                }
                println(counter)
                counter++
            }
        } finally {
            println("Disconnecting Game Client...")
            eventLoopGroup.shutdownGracefully()
            println("Game Client disconnected")
        }
    }

}