package net.pipeline

import io.netty.buffer.ByteBuf
import io.netty.buffer.Unpooled
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.MessageToMessageCodec
import net.message.GameMessage
import net.codec.Codec
import net.protocol.Protocol

class CodecsHandler(private var protocol: Protocol) : MessageToMessageCodec<ByteBuf, GameMessage>() {

    override fun encode(context: ChannelHandlerContext, message: GameMessage, out: MutableList<Any>) {
        // find codec
        val reg: Codec.CodecRegistration = protocol.getCodecRegistration(message.javaClass)

        val headerBuf: ByteBuf = context.alloc().buffer(8)
        val messageBuf: ByteBuf = context.alloc().buffer()

        out.add(Unpooled.wrappedBuffer(headerBuf, messageBuf))
    }

    override fun decode(context: ChannelHandlerContext, message: ByteBuf, out: MutableList<Any>) {
    }

}