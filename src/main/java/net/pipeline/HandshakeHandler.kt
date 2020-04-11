package net.pipeline

import net.message.handshake.HandshakeMessage
import net.message.MessageHandler
import net.session.BasicSession

class HandshakeHandler : MessageHandler<BasicSession, HandshakeMessage> {
    override fun handle(session: BasicSession, message: HandshakeMessage) {
        //
    }

}
