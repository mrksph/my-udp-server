package com.rozularen.net.handler.login

import com.rozularen.net.handler.GameMessageHandler
import com.rozularen.net.http.HttpClient
import com.rozularen.net.message.login.LoginStartMessage
import com.rozularen.net.session.GameSession

class LoginStartHandler (var httpClient: HttpClient)
    : GameMessageHandler<GameSession, LoginStartMessage>() {


    override fun internalHandle(session: GameSession, message: LoginStartMessage) {
        val name = message.username
        val server = session.server

        if(server.ONLINE_MODE) {

        }
        httpClient.connect()

    }
}