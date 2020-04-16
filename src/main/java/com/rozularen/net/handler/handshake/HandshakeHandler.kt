package com.rozularen.net.handler.handshake

import com.rozularen.net.MainServer
import com.rozularen.net.handler.GameMessageHandler
import com.rozularen.net.message.handshake.HandshakeMessage
import com.rozularen.net.protocol.BaseProtocol
import com.rozularen.net.protocol.login.LoginProtocol
import com.rozularen.net.session.GameSession
import java.net.InetSocketAddress

class HandshakeHandler (var loginProtocol: LoginProtocol)
    : GameMessageHandler<GameSession, HandshakeMessage>() {
    override fun internalHandle(session: GameSession, message: HandshakeMessage) {
        val state = message.state
        val protocol: BaseProtocol
        println("ESTAMOS 1111111111111111111111111111111")
        println("ESTAMOS 1111111111111111111111111111111")
        println("ESTAMOS 1111111111111111111111111111111")
        println("ESTAMOS 1111111111111111111111111111111")
        println("ESTAMOS 1111111111111111111111111111111")
        println("ESTAMOS 1111111111111111111111111111111")
        println("ESTAMOS 1111111111111111111111111111111")
        println("ESTAMOS 1111111111111111111111111111111")
        println("ESTAMOS 1111111111111111111111111111111")
        println("ESTAMOS 1111111111111111111111111111111")
        println("ESTAMOS 1111111111111111111111111111111")
        println("ESTAMOS 1111111111111111111111111111111")
        println("ESTAMOS 1111111111111111111111111111111")
        println("ESTAMOS 1111111111111111111111111111111")
        println("ESTAMOS 1111111111111111111111111111111")


        if (state == 1) {
            protocol = loginProtocol
        } else {
            session.disconnect("Invalid state")
            return
        }

        session.version = message.version
        session.virtualHost = InetSocketAddress.createUnresolved(message.address, message.port)
        session.protocol = protocol

        if (message.version < MainServer.PROTOCOL_VERSION) {
            session.disconnect("Outdated net.client")
        } else if (message.version > MainServer.PROTOCOL_VERSION) {
            session.disconnect("Outdated server")
        }

    }

}
