package net.codec.handshake

import io.netty.buffer.ByteBuf
import net.codec.GameCodec
import net.message.handshake.HandshakeMessage

class HandshakeCodec : GameCodec<HandshakeMessage> {
    override fun encode(var1: ByteBuf): HandshakeMessage {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun decode(var1: ByteBuf, var2: HandshakeMessage): ByteBuf {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}