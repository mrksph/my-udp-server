package net.session

import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap

class SessionRegistry {

    private val sessions: ConcurrentMap<BaseSession, Boolean> = ConcurrentHashMap()

    fun add(baseSession: BaseSession) = sessions.put(baseSession, true)

    fun pulse() = sessions.keys.forEach { it.pulse() }

    fun remove(baseSession: BaseSession) = sessions.remove(baseSession)

}