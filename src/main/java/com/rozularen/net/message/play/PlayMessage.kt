package com.rozularen.net.message.play

import com.rozularen.net.message.GameMessage

data class PlayMessage(val id: Int)
    : GameMessage {

    override val name: String= "PlayMessage"

}