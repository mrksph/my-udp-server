package net.protocol

import net.http.HttpClient
import net.protocol.handshake.HandshakeProtocol
import net.protocol.login.LoginProtocol
import net.protocol.play.PlayProtocol


class ProtocolProvider {
    val HANDSHAKE: HandshakeProtocol
    //  val status: StatusProtocol
      val login: LoginProtocol
    val PLAY: PlayProtocol

    init {
        //  status = StatusProtocol()
        val httpClient = HttpClient()
          login = LoginProtocol(httpClient)
        //  handshake = HandshakeProtocol(status, login)
        HANDSHAKE = HandshakeProtocol(login)
        PLAY = PlayProtocol()
    }
}
