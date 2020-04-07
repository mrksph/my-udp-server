package com.pingpong.net.sample

import io.netty.channel.ChannelHandlerContext
import io.netty.channel.SimpleChannelInboundHandler
import java.net.DatagramPacket

class GameClientHandler : SimpleChannelInboundHandler<DatagramPacket>() {

    override fun channelRead0(context: ChannelHandlerContext, packet: DatagramPacket) {
        val response = packet.data
    }

}