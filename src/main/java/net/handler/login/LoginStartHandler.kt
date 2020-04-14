package net.handler.login

import net.handler.GameMessageHandler
import net.message.login.LoginStartMessage
import net.session.GameSession

class LoginStartHandler() :GameMessageHandler<GameSession, LoginStartMessage>{
    override fun handle(session: GameSession, message: LoginStartMessage) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}