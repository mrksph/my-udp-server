package com.rozularen.net.pipeline

import com.rozularen.net.protocol.GameProtocol
import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.socket.DatagramPacket
import io.netty.handler.codec.MessageToMessageCodec

class CodecsHandler(private var protocol: GameProtocol) : MessageToMessageCodec<DatagramPacket, ByteBuf>() {

    override fun encode(context: ChannelHandlerContext, buffer: ByteBuf, out: MutableList<Any>) {
        val headerBuffer = context.alloc().buffer(8)


    }

    override fun decode(context: ChannelHandlerContext, packet: DatagramPacket, out: MutableList<Any>) {
        val content = packet.content()
        val readableBytes = content.readableBytes()

        val codec = protocol.readHeader(content)
        val decoded = codec.decode(content)

        if (content.readableBytes() > 0) {
            System.err.println("Message too long")
        }
        out.add(decoded)
    }

}