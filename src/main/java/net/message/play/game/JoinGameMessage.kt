package net.message.play.game

import net.message.GameMessage

data class JoinGameMessage(val name: String = "JoinGameMessage",
                           val id : Int)
    : GameMessage {

}