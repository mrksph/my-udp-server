package net.message.handshake

import net.message.GameMessage

data class HandshakeMessage(val version: Int = 0,
                       val address: String = "",
                       val port: Int = 0,
                       val state: Int = 0)
    : GameMessage("HandshakeMessage") {


}