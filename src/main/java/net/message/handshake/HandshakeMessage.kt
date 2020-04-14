package net.message.handshake

import net.message.GameMessage

class HandshakeMessage(val version: Int,
                            val address: String,
                            val port: Int,
                            val state: Int) : GameMessage {


}