package net.message

abstract class AsyncGameMessage(name: String, val isAsync: Boolean)
    : GameMessage(name) {
}