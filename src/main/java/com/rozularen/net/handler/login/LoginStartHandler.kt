package com.rozularen.net.handler.login

import com.rozularen.entity.meta.PlayerProfile
import com.rozularen.net.handler.GameMessageHandler
import com.rozularen.net.http.HttpClient
import com.rozularen.net.message.login.LoginStartMessage
import com.rozularen.net.session.GameSession
import java.util.*

class LoginStartHandler (private var httpClient: HttpClient)
    : GameMessageHandler<GameSession, LoginStartMessage>() {


    override fun internalHandle(session: GameSession, message: LoginStartMessage) {
        val name = message.username
        val server = session.server

        if(server.ONLINE_MODE) {
           //TODO: THIS SHOULD BE DONE _AFTER_ LOGIN ON ANOTTHER SERVICE
            session.server.scheduler.runTask(Runnable {
                val uuid = UUID.randomUUID()
                val profile = PlayerProfile(name, uuid.toString())
                session.setPlayer(profile)
            })
        }

        httpClient.connect()

    }
}