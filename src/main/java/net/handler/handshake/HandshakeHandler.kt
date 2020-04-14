package net.handler.handshake

import net.MainServer
import net.handler.GameMessageHandler
import net.message.handshake.HandshakeMessage
import net.protocol.BaseProtocol
import net.protocol.login.LoginProtocol
import net.session.GameSession
import java.net.InetSocketAddress

class HandshakeHandler(var loginProtocol: LoginProtocol)
    : GameMessageHandler<GameSession, HandshakeMessage> {
    override fun internalHandle(session: GameSession, message: HandshakeMessage) {
        val state = message.state
        val protocol: BaseProtocol

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
            session.disconnect("Outdated client")
        } else if (message.version > MainServer.PROTOCOL_VERSION) {
            session.disconnect("Outdated server")
        }

    }

}
