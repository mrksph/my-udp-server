package game

import DEFAULT_PORT
import io.netty.bootstrap.Bootstrap
import io.netty.channel.ChannelOption
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.nio.NioDatagramChannel

object GameServer {

    fun bind() {
        val eventLoopGroup = NioEventLoopGroup()

        try {
            val b = Bootstrap()

            //Create an UDP server, with a Broadcast Option
            //Also sets the handler to be called when a message is received
            b.group(eventLoopGroup)
                    .channel(NioDatagramChannel::class.java)
                    .option(ChannelOption.SO_BROADCAST, true)
                    .handler(GameServerHandler)

            println("Game Server created succesfully")

            b.bind(DEFAULT_PORT)
                    .sync()
                    .channel()
                    .closeFuture()
                    .await()
        } finally {
            println("Disconnecting Game Server...")
            eventLoopGroup.shutdownGracefully()
            println("Game Server disconnected succesfully")
        }
    }

    @JvmStatic
    fun main(args: Array<String>) {
        println("Setting up Game Server (Port: ${DEFAULT_PORT})")
        bind()
    }
}
