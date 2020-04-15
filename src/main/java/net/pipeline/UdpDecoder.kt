package net.pipeline

import io.netty.channel.ChannelHandlerContext
import io.netty.channel.socket.DatagramPacket
import io.netty.handler.codec.MessageToMessageDecoder

class UdpDecoder : MessageToMessageDecoder<DatagramPacket>() {
    override fun decode(context: ChannelHandlerContext, packet: DatagramPacket, out: MutableList<Any>) {
        println("HOOOOOLAAA")
        context.fireChannelRead(packet)
    }
}
