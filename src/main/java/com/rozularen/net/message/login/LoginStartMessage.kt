package com.rozularen.net.message.login

import com.rozularen.net.message.AsyncGameMessage

data class LoginStartMessage(val username: String = "")
    : AsyncGameMessage {

    override val name = "LoginStartMessage"
    override val isAsync = true
}