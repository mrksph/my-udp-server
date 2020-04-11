package net.message

interface GameMessage {
    override fun toString(): String

    override fun equals(var1: Any?): Boolean

    override fun hashCode(): Int
}