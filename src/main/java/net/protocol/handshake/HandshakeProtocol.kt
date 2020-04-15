package net.protocol.handshake

import net.codec.handshake.HandshakeCodec
import net.handler.handshake.HandshakeHandler
import net.message.handshake.HandshakeMessage
import net.protocol.BaseProtocol
import net.protocol.login.LoginProtocol

class HandshakeProtocol(loginProtocol: LoginProtocol)
    : BaseProtocol("HANDSHAKE", 1) {

    init {
        val message = HandshakeMessage()
        val codec = HandshakeCodec()
        val handshakeHandler = HandshakeHandler(loginProtocol)

        inbound(0x00,
                message,
                codec,
                handshakeHandler)
    }

}