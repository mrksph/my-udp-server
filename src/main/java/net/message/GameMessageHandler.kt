package net.message

import net.session.BaseSession

interface GameMessageHandler<S : BaseSession, T : GameMessage> {
    fun handle(session: S, message: T)
}
