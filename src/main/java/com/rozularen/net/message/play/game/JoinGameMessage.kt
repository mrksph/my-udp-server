package com.rozularen.net.message.play.game

import com.rozularen.net.message.GameMessage

data class JoinGameMessage(val name: String = "JoinGameMessage",
                           val id : Int)
    : GameMessage {

}