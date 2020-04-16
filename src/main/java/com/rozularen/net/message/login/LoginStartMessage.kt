package com.rozularen.net.message.login

import com.rozularen.net.message.AsyncGameMessage

data class LoginStartMessage(val id: Int = 0,
                             val username: String = "")
    : AsyncGameMessage {

    private val name = "LoginStartMessage"

    override fun getName(): String {
        return name
    }

    override fun isAsync(): Boolean {
        return true
    }
}