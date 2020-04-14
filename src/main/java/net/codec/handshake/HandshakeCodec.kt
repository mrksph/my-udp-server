package net.codec.handshake

import io.netty.buffer.ByteBuf
import io.netty.util.CharsetUtil
import net.codec.GameCodec
import net.message.handshake.HandshakeMessage
import java.nio.ByteBuffer

class HandshakeCodec : GameCodec<HandshakeMessage> {
    override fun encode(buffer: ByteBuf): HandshakeMessage {
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

    override fun decode(buffer: ByteBuf, message: HandshakeMessage): ByteBuf {
        val firstMessage = "HELLO WORLD"
        buffer.writeBytes(firstMessage.toByteArray(Charsets.UTF_8))
        return buffer
    }
}