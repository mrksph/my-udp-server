package com.pingpong.net.game

import io.netty.bootstrap.Bootstrap
import io.netty.channel.ChannelOption
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.nio.NioDatagramChannel
import java.net.InetSocketAddress

object GameServer {
    private const val DEFAULT_PORT = 31047

    fun bind(port: Int = DEFAULT_PORT) = bind(InetSocketAddress(port))

    private fun bind(address: InetSocketAddress) {
        val eventLoopGroup = NioEventLoopGroup()

        try {
            val b = Bootstrap()

            b.group(eventLoopGroup)
                    .channel(NioDatagramChannel::class.java)
                    .option(ChannelOption.SO_BROADCAST, true)
                    .handler(GameServerHandler())
                    .bind(address)
                    .sync()
                    .channel()
                    .closeFuture()
                    .await()

        } finally {
           eventLoopGroup.shutdownGracefully()
        }
    }
}
