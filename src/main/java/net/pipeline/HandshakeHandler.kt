package net.pipeline

import net.message.handshake.HandshakeMessage
import net.message.MessageHandler
import net.session.Session

class HandshakeHandler : MessageHandler<Session, HandshakeMessage> {
    override fun handle(session: Session, message: HandshakeMessage) {
        //
    }

}
