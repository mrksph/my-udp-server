package com.rozularen.net.message

interface AsyncGameMessage
    : GameMessage {

    fun isAsync() : Boolean
}