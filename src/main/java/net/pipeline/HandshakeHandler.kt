package net.pipeline

import net.message.handshake.HandshakeMessage
import net.message.MessageHandler
import net.session.GameSession

class HandshakeHandler : MessageHandler<GameSession, HandshakeMessage> {
    override fun handle(gameSession: GameSession, message: HandshakeMessage) {
        //
    }

}
