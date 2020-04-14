package net.handler

import net.message.GameMessage
import net.session.BaseSession

abstract class GameMessageHandler<S : BaseSession, T : GameMessage> {
    abstract fun handle(session: S, message: T)
}
