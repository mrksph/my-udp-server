package net.pipeline

import io.netty.buffer.ByteBuf
import io.netty.buffer.Unpooled
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.socket.DatagramPacket
import io.netty.handler.codec.MessageToMessageCodec
import net.codec.GameCodec
import net.message.GameMessage
import net.protocol.GameProtocol

class CodecsHandler(private var protocol: GameProtocol) : MessageToMessageCodec<DatagramPacket, ByteBuf>() {
    override fun encode(p0: ChannelHandlerContext?, p1: ByteBuf?, p2: MutableList<Any>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun decode(p0: ChannelHandlerContext?, p1: DatagramPacket?, p2: MutableList<Any>?) {
        println("HOLA")
    }


}