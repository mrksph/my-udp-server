package com.rozularen.net.message.play.game

import com.rozularen.net.message.GameMessage

data class JoinGameMessage(val id: Int)
    : GameMessage {

    override val name = "LoginStartMessage"

}