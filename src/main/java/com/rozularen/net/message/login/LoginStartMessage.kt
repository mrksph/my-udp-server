package com.rozularen.net.message.login

import com.rozularen.net.message.GameMessage

data class LoginStartMessage(val id: Int = 0,
                             val username: String= "")
    : GameMessage {

    private val name = "LoginStartMessage"

    override fun getName(): String {
        return name
    }
}