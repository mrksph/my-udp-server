package com.rozularen.net.message

interface AsyncGameMessage
    : GameMessage {

    val isAsync : Boolean

}