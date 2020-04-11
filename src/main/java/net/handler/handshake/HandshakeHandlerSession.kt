package net.handler.handshake

import net.message.handshake.HandshakeMessage
import net.message.GameMessageHandler
import net.session.GameSession

class HandshakeHandlerSession : GameMessageHandler<GameSession, HandshakeMessage> {
    override fun handle(gameSession: GameSession, message: HandshakeMessage) {
        //
    }

}
