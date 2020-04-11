package net.message

import net.session.Session

interface MessageHandler<S : Session, T : Message> {
    fun handle(session: S, message: T)
}
