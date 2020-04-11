package protocol

import net.protocol.HandshakeProtocol


class ProtocolProvider {
    val HANDSHAKE: HandshakeProtocol
    //    val status: StatusProtocol
//    val login: LoginProtocol
    val PLAY: PlayProtocol

    init {
//        status = StatusProtocol()
//        login = LoginProtocol(httpClient)
//        handshake = HandshakeProtocol(status, login)
        HANDSHAKE = HandshakeProtocol()
        PLAY = PlayProtocol()
    }
}
