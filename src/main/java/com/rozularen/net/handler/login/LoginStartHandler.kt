package com.rozularen.net.handler.login

import com.rozularen.entity.meta.PlayerProfile
import com.rozularen.net.handler.GameMessageHandler
import com.rozularen.net.http.HttpClient
import com.rozularen.net.message.login.LoginStartMessage
import com.rozularen.net.session.GameSession

class LoginStartHandler (var httpClient: HttpClient)
    : GameMessageHandler<GameSession, LoginStartMessage>() {


    override fun internalHandle(session: GameSession, message: LoginStartMessage) {
        val name = message.username
        val server = session.server
        println("XzKLKMLKLMLKMLK 222222222222222222222222222222222222")
        println("XzKLKMLKLMLKMLK 222222222222222222222222222222222222")
        println("XzKLKMLKLMLKMLK 222222222222222222222222222222222222")
        println("XzKLKMLKLMLKMLK 222222222222222222222222222222222222")
        println("XzKLKMLKLMLKMLK 222222222222222222222222222222222222")
        println("XzKLKMLKLMLKMLK 222222222222222222222222222222222222")
        println("XzKLKMLKLMLKMLK 222222222222222222222222222222222222")
        println("XzKLKMLKLMLKMLK 222222222222222222222222222222222222")
        println("XzKLKMLKLMLKMLK 222222222222222222222222222222222222")
        println("XzKLKMLKLMLKMLK 222222222222222222222222222222222222")
        println("XzKLKMLKLMLKMLK 222222222222222222222222222222222222")
        println("XzKLKMLKLMLKMLK 222222222222222222222222222222222222")
        println("XzKLKMLKLMLKMLK 222222222222222222222222222222222222")
        println("XzKLKMLKLMLKMLK 222222222222222222222222222222222222")
        println("XzKLKMLKLMLKMLK 222222222222222222222222222222222222")


        if(server.ONLINE_MODE) {
           //TODO: THIS SHOULD BE DONE _AFTER_ LOGIN ON ANOTTHER SERVICE
            session.server.scheduler.runTask(Runnable {
                val profile = PlayerProfile(name)
                session.setPlayer(profile)
            })
        }


        httpClient.connect()

    }
}