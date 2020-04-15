package net.message.play

import net.message.GameMessage

data class PlayMessage(val name: String= "PlayMessage",
                       val id: Int)
    : GameMessage {

}