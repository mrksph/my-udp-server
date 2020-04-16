package com.rozularen.net.message.play

import com.rozularen.net.message.GameMessage

data class PlayMessage(val id: Int)
    : GameMessage {

    private val name: String= "PlayMessage"

    override fun getName(): String {
        return name
    }

}