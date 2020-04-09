package net.session

import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap

class SessionRegistry {

    private val sessions: ConcurrentMap<Session, Boolean> = ConcurrentHashMap()

    fun add(session: Session) = sessions.put(session, true)

    fun pulse() = sessions.keys.forEach { it.pulse() }

    fun remove(session: Session) = sessions.remove(session)

}