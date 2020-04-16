package com.rozularen.net.handler.login

import com.rozularen.net.handler.GameMessageHandler
import com.rozularen.net.http.HttpClient
import com.rozularen.net.message.login.LoginStartMessage
import com.rozularen.net.session.GameSession

class LoginStartHandler constructor(var httpClient: HttpClient)
    : GameMessageHandler<GameSession, LoginStartMessage>() {


    override fun internalHandle(session: GameSession, message: LoginStartMessage) {
        val sessionId = session.sessionId


//        httpClient.connect()

    }
}