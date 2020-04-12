package net.handler

import net.message.GameMessage
import net.session.BaseSession

interface GameMessageHandler<S : BaseSession, T : GameMessage> {
    fun handle(session: S, message: T)
}
