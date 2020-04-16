package com.rozularen.client

import io.netty.bootstrap.Bootstrap
import io.netty.buffer.ByteBuf
import io.netty.buffer.ByteBufAllocator
import io.netty.channel.ChannelOption
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.DatagramPacket
import io.netty.channel.socket.nio.NioDatagramChannel
import io.netty.util.CharsetUtil
import io.netty.util.internal.SocketUtils

class GameClient {

    fun connect() {
        val eventLoopGroup = NioEventLoopGroup()
        try {

            val bootstrap = Bootstrap()
                    .channel(NioDatagramChannel::class.java)
                    .group(eventLoopGroup)
                    .option(ChannelOption.SO_BROADCAST, true)
                    .handler(GameClientHandler)

            println("Game Client is up")

            val channel = bootstrap
                    .bind(0)
                    .sync()
                    .channel()

            val buffer: ByteBuf = ByteBufAllocator.DEFAULT.buffer(4)
            buffer.writeInt(0x00)
            buffer.writeInt(0x01)
            buffer.writeBytes("HOLA".toByteArray(CharsetUtil.UTF_8))


            val packet = DatagramPacket(
                    buffer,
                    SocketUtils.socketAddress("255.255.255.255", 31047),
                    SocketUtils.socketAddress("0.0.0.0", 31047))

            channel.writeAndFlush(packet).sync()
            println("Write package")

        } finally {
            println("Disconnecting Game Client...")
            eventLoopGroup.shutdownGracefully()
            println("Game Client disconnected")
        }
    }

}