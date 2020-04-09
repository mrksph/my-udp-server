package net.message

interface Message {
    override fun toString(): String

    override fun equals(var1: Any?): Boolean

    override fun hashCode(): Int
}