package com.rozularen.net.protocol

import com.rozularen.net.http.HttpClient
import com.rozularen.net.protocol.handshake.HandshakeProtocol
import com.rozularen.net.protocol.login.LoginProtocol
import com.rozularen.net.protocol.play.PlayProtocol


object ProtocolProvider {
    //  val status: StatusProtocol
    val httpClient: HttpClient = object : HttpClient {
        override fun connect() {

        }
    }
    val LOGIN: LoginProtocol = LoginProtocol(httpClient)
    val HANDSHAKE: HandshakeProtocol = HandshakeProtocol(LOGIN)
    val PLAY: PlayProtocol = PlayProtocol()


    init {
        println("Init ProtocolProvider")
    }
    //        //  status = StatusProtocol()
//        val httpClient: HttpClient = object : HttpClient {
//            override fun connect() {
//
//            }
//        }
//        login = LoginProtocol(httpClient)
//        //  handshake = HandshakeProtocol(status, login)
//        HANDSHAKE = HandshakeProtocol(login)
//        PLAY = PlayProtocol()
//    }
}
