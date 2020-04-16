package net.pipeline

import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.socket.DatagramPacket
import io.netty.handler.codec.MessageToMessageCodec
import net.protocol.GameProtocol

class CodecsHandler(private var protocol: GameProtocol) : MessageToMessageCodec<DatagramPacket, ByteBuf>() {
    override fun encode(p0: ChannelHandlerContext?, p1: ByteBuf?, p2: MutableList<Any>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun decode(context: ChannelHandlerContext, packet: DatagramPacket, out: MutableList<Any>) {
        val content = packet.content()
        val codec = protocol.readHeader(content)
        val decoded = codec.decode(content)
        val readableBytes = content.readableBytes()

        if (readableBytes > 0) {
            System.err.println("Message too long")
        }
        out.add(decoded)
    }

}