package net.codec.handshake

import io.netty.buffer.ByteBuf
import net.codec.GameCodec
import net.message.handshake.HandshakeMessage

class HandshakeCodec : GameCodec<HandshakeMessage> {
    override fun encode(buffer: ByteBuf): HandshakeMessage {
        val version = buffer.readInt()
//        val address: String = buffer.read
        //TODO: HACER ESTO CUANTO ANTES
        return HandshakeMessage(version, "", 0, 1)
    }

    override fun decode(var1: ByteBuf, var2: HandshakeMessage): ByteBuf {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}