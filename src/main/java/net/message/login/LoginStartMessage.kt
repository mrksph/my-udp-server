package net.message.login

import net.message.GameMessage

data class LoginStartMessage(val id : Int)
    : GameMessage {
}