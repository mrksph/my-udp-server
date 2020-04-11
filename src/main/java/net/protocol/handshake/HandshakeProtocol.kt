package net.protocol.handshake

import net.codec.handshake.HandshakeCodec
import net.message.handshake.HandshakeMessage
import net.pipeline.HandshakeHandler
import net.protocol.BaseProtocol

class HandshakeProtocol
    : BaseProtocol("HANDSHAKE", 1) {

    init {
        inbound(0x00,
                HandshakeMessage::class.java,
                HandshakeCodec::class.java,
                HandshakeHandler())
    }

}