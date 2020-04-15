package net.handler.login

import net.handler.GameMessageHandler
import net.http.HttpClient
import net.message.login.LoginStartMessage
import net.session.GameSession

class LoginStartHandler constructor(var httpClient: HttpClient)
    : GameMessageHandler<GameSession, LoginStartMessage>() {


    override fun internalHandle(session: GameSession, message: LoginStartMessage) {
        val sessionId = session.sessionId


//        httpClient.connect()

    }
}