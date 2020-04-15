package net.pipeline

import io.netty.buffer.ByteBuf
import io.netty.buffer.Unpooled
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.socket.DatagramPacket
import io.netty.handler.codec.MessageToMessageCodec
import net.codec.GameCodec
import net.message.GameMessage
import net.protocol.GameProtocol

class CodecsHandler(private var protocol: GameProtocol) : MessageToMessageCodec<ByteBuf, DatagramPacket>() {

    override fun encode(context: ChannelHandlerContext, message: DatagramPacket, out: MutableList<Any>) {
//        // find codec
//        val reg: GameCodec.CodecRegistration = protocol.getCodecRegistration(message)
//
//        val headerBuf: ByteBuf = context.alloc().buffer(8)
//        //Write to buffer opcode
//
//
//        val messageBuf: ByteBuf = context.alloc().buffer()
//
//        out.add(Unpooled.wrappedBuffer(headerBuf, messageBuf))
    }

    override fun decode(context: ChannelHandlerContext, message: ByteBuf, out: MutableList<Any>) {
        val codec = protocol.readHeader(message)

        val decodedMessage = codec.decode(message)

        if (message.readableBytes() > 0) {
            System.err.println("Message too long")
        }

        out.add(decodedMessage)
    }

}