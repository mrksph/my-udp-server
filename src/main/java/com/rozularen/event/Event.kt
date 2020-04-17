package com.rozularen.event

abstract class Event(val isAsync: Boolean = false) {

    val handlers: HandlersList = HandlersList()

    private val name: String = this.javaClass.simpleName

    enum class Result {
        DENY,
        DEFAULT,
        ALLOW
    }

}