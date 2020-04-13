package net.message

interface AsyncGameMessage : GameMessage {
    val isAsync: Boolean
}