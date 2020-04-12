package net.protocol

import net.protocol.handshake.HandshakeProtocol
import net.protocol.play.PlayProtocol


class ProtocolProvider {
    val HANDSHAKE: HandshakeProtocol
    //  val status: StatusProtocol
    //  val login: LoginProtocol
    val PLAY: PlayProtocol

    init {
        //  status = StatusProtocol()
        //  login = LoginProtocol(httpClient)
        //  handshake = HandshakeProtocol(status, login)
        HANDSHAKE = HandshakeProtocol()
        PLAY = PlayProtocol()
    }
}
