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

            var buffer: ByteBuf = ByteBufAllocator.DEFAULT.buffer(4)


            val version = 1
            val senderAddress = "0.0.0.0"
            val port = 31047
            val state = 1

            // HEADER, OPCODE
            buffer.writeInt(0x00)

            // BODY FOR THIS MESSAGE
            // VERSION, ADDRESS, PORT, STATE
            buffer.writeInt(version)
            buffer.writeBytes(senderAddress.toByteArray(CharsetUtil.UTF_8))
            buffer.writeInt(port)
            buffer.writeInt(state)

            var packet = DatagramPacket(
                    buffer,
                    SocketUtils.socketAddress("255.255.255.255", 31047),
                    SocketUtils.socketAddress(senderAddress, 31047))

            channel.writeAndFlush(packet).sync()
            println("Write HANDSHAKE package")


            buffer = ByteBufAllocator.DEFAULT.buffer(4)
            // HEADER, OPCODE
            buffer.writeInt(0x00)
            // BODY FOR THIS MESSAGE
            // VERSION, ADDRESS, PORT, STATE
            buffer.writeBytes("marcos".toByteArray(CharsetUtil.UTF_8))

             packet = DatagramPacket(
                    buffer,
                    SocketUtils.socketAddress("255.255.255.255", 31047),
                    SocketUtils.socketAddress(senderAddress, 31047))

            channel.writeAndFlush(packet).sync()
            println("Write LOGIN package")

        } catch(e: RuntimeException) {
            e.printStackTrace()
        }
        finally {
            println("Disconnecting Game Client...")
            eventLoopGroup.shutdownGracefully()
            println("Game Client disconnected")
        }
    }

}