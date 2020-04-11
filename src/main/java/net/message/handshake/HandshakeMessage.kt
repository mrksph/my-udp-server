package net.message.handshake

import net.message.GameMessage

data class HandshakeMessage(val version: Int,
                            val address: String,
                            val port: Int,
                            val state: Int) : GameMessage {


    override fun toString(): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun equals(var1: Any?): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun hashCode(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}