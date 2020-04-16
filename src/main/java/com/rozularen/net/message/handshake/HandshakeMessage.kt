package com.rozularen.net.message.handshake

import com.rozularen.net.message.GameMessage

data class HandshakeMessage(val version: Int = 0,
                            val address: String = "",
                            val port: Int = 0,
                            val state: Int = 0)
    : GameMessage {

}