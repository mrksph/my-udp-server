package net.handler.handshake

import net.message.handshake.HandshakeMessage
import net.handler.GameMessageHandler
import net.session.GameSession

class HandshakeHandler : GameMessageHandler<GameSession, HandshakeMessage> {
    override fun handle(session: GameSession, message: HandshakeMessage) {
        //
    }

}
