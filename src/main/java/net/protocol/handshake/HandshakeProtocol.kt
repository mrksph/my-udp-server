package net.protocol.handshake

import net.codec.handshake.HandshakeCodec
import net.message.handshake.HandshakeMessage
import net.handler.handshake.HandshakeHandler
import net.protocol.BaseProtocol
import net.protocol.ProtocolProvider
import net.protocol.login.LoginProtocol

class HandshakeProtocol(loginProtocol: LoginProtocol)
    : BaseProtocol("HANDSHAKE", 1) {

    init {
        inbound(0x00,
                HandshakeMessage::class.java,
                HandshakeCodec::class.java,
                HandshakeHandler(loginProtocol))
    }

}