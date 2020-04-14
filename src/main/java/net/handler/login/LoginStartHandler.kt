package net.handler.login

import net.handler.GameMessageHandler
import net.http.HttpClient
import net.message.login.LoginStartMessage
import net.session.GameSession

class LoginStartHandler(val httpClient: HttpClient)
    : GameMessageHandler<GameSession, LoginStartMessage> {
    override fun handle(session: GameSession, message: LoginStartMessage) {
        val sessionId = session.sessionId
    }
}