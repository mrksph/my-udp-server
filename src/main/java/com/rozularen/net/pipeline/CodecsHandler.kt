package com.rozularen.net.pipeline

import com.rozularen.net.codec.GameCodec
import com.rozularen.net.message.GameMessage
import com.rozularen.net.protocol.GameProtocol
import io.netty.buffer.Unpooled
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.socket.DatagramPacket
import io.netty.handler.codec.MessageToMessageCodec

class CodecsHandler(private var protocol: GameProtocol)
    : MessageToMessageCodec<DatagramPacket, GameMessage>() {

    override fun encode(context: ChannelHandlerContext, message: GameMessage, out: MutableList<Any>) {


        val codecRegistration = protocol.getCodecRegistration(message)

        var headerBuffer = context.alloc().buffer(8)
        headerBuffer.writeInt(codecRegistration.opcode)

        var messageBuffer = context.alloc().buffer(8)
        val codec: GameCodec<in GameMessage> = codecRegistration.codec as GameCodec<in GameMessage>
        messageBuffer = codec.encode(messageBuffer, message)

        println("ENCODING USING ${codecRegistration.codec.getCodecName()}")


        out.add(Unpooled.wrappedBuffer(headerBuffer, messageBuffer))
    }

    override fun decode(context: ChannelHandlerContext, packet: DatagramPacket, out: MutableList<Any>) {
        val content = packet.content()
        val readableBytes = content.readableBytes()

        val codec = protocol.readHeader(content)
        println("THE CODEC NOW USING IS: ${codec.getCodecName()}")
        val decoded = codec.decode(content)

        if (content.readableBytes() > 0) {
            System.err.println("Message too long")
        }
        out.add(decoded)
    }

}