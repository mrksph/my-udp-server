package com.rozularen.event

abstract class Event(val isAsync: Boolean) {

    constructor() : this(false)

    private val name: String = this.javaClass.simpleName

    abstract fun getHandlers() : HandlersList

    enum class Result {
        DENY,
        DEFAULT,
        ALLOW
    }

}