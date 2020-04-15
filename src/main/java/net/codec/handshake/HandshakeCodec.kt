package net.codec.handshake

import io.netty.buffer.ByteBuf
import net.codec.GameCodec
import net.message.handshake.HandshakeMessage

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