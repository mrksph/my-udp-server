package net.message

import net.session.BaseSession

interface MessageHandler<S : BaseSession, T : Message> {
    fun handle(session: S, message: T)
}
