package net.protocol

import net.message.HandshakeMessage

class HandshakeProtocol
    : AbstractProtocol("HANDSHAKE", 1) {

    init {
        inbound(0x00,
                HandshakeMessage::class.java,
                HandshakeCodec::class.java,
                HandshakeHandler())
    }

}