package com.rozularen.net.codec.handshake

import com.rozularen.net.codec.GameCodec
import com.rozularen.net.message.handshake.HandshakeMessage
import io.netty.buffer.ByteBuf
import io.netty.util.CharsetUtil

class HandshakeCodec
    : GameCodec<HandshakeMessage>() {
    private val name = "HandshakeCodec"

    override fun decode(buffer: ByteBuf): HandshakeMessage {
        val version = buffer.readInt()
        val address: String = buffer.readBytes(7).toString(CharsetUtil.UTF_8)
        val port = buffer.readInt()
        val state = buffer.readInt()

        return HandshakeMessage(version, address, port, state)
    }

    override fun encode(buffer: ByteBuf, message: HandshakeMessage): ByteBuf {
        val firstMessage = "HELLO WORLD"

        buffer.writeBytes(firstMessage.toByteArray(Charsets.UTF_8))

        return buffer
    }


    override fun getCodecName(): String {
        return name
    }
}