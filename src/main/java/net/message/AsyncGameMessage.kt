package net.message

interface AsyncGameMessage
    : GameMessage {

    fun isAsync() : Boolean
}