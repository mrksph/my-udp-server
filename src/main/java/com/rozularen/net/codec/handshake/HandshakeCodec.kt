package com.rozularen.net.codec.handshake

import com.rozularen.net.codec.GameCodec
import com.rozularen.net.message.handshake.HandshakeMessage
import io.netty.buffer.ByteBuf

class HandshakeCodec : GameCodec<HandshakeMessage>() {
    private val name = "HandshakeCodec"

    override fun decode(buffer: ByteBuf): HandshakeMessage {
        val version = buffer.readInt()
//        val address: String = buffer.read
        //TODO: HACER ESTO CUANTO ANTES
        println("------------------------------ $version")
        println("------------------------------ $version")
        println("------------------------------ $version")
        println("------------------------------ $version")
        println("------------------------------ $version")
        println("------------------------------ $version")
        return HandshakeMessage(version, "", 0, 1)
    }

    override fun encode(buffer: ByteBuf, message: HandshakeMessage): ByteBuf {

        val firstMessage = "HELLO WORLD"
        buffer.writeBytes(firstMessage.toByteArray(Charsets.UTF_8))

        return buffer
    }


    override fun getCodecName(): String {
        return name;
    }
}