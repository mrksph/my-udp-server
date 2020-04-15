package net.pipeline

import io.netty.channel.ChannelHandlerContext
import io.netty.channel.socket.DatagramPacket
import io.netty.handler.codec.MessageToMessageEncoder

class UdpEncoder : MessageToMessageEncoder<DatagramPacket>() {
    override fun encode(context: ChannelHandlerContext, packet: DatagramPacket, out: MutableList<Any>) {

    }
}