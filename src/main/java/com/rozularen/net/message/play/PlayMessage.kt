package com.rozularen.net.message.play

import com.rozularen.net.message.GameMessage

data class PlayMessage(val name: String= "PlayMessage",
                       val id: Int)
    : GameMessage {

}