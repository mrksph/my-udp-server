package com.rozularen.net.protocol

import com.rozularen.net.http.HttpClient
import com.rozularen.net.protocol.handshake.HandshakeProtocol
import com.rozularen.net.protocol.login.LoginProtocol
import com.rozularen.net.protocol.play.PlayProtocol


class ProtocolProvider {
    val HANDSHAKE: HandshakeProtocol
    //  val status: StatusProtocol
    val login: LoginProtocol
    val PLAY: PlayProtocol

    init {
        //  status = StatusProtocol()
        val httpClient: HttpClient = object : HttpClient {
            override fun connect() {

            }
        }
        login = LoginProtocol(httpClient)
        //  handshake = HandshakeProtocol(status, login)
        HANDSHAKE = HandshakeProtocol(login)
        PLAY = PlayProtocol()
    }
}
