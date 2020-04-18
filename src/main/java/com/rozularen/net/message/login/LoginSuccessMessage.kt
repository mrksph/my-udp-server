package com.rozularen.net.message.login

import com.rozularen.net.message.GameMessage

data class LoginSuccessMessage(val uuid: String = "",
                               val username: String = "")
    : GameMessage {

    private val name = "LoginStartMessage"

    override fun getName(): String {
        return name
    }

}
