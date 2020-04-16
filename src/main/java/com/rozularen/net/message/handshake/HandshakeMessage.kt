package com.rozularen.net.message.handshake

import com.rozularen.net.message.AsyncGameMessage


data class HandshakeMessage(val version: Int = 0,
                            val address: String = "",
                            val port: Int = 0,
                            val state: Int = 0)
    : AsyncGameMessage {

    private val name = "HandshakeMessage"

    override fun isAsync(): Boolean {
        return true
    }

    override fun getName(): String {
        return name
    }

}