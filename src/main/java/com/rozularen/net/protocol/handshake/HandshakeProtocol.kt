package com.rozularen.net.protocol.handshake

import com.rozularen.net.codec.handshake.HandshakeCodec
import com.rozularen.net.handler.handshake.HandshakeHandler
import com.rozularen.net.message.handshake.HandshakeMessage
import com.rozularen.net.protocol.BaseProtocol
import com.rozularen.net.protocol.login.LoginProtocol

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