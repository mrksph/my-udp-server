package com.rozularen.entity.player

import com.rozularen.entity.BaseEntity
import com.rozularen.entity.location.Location
import com.rozularen.entity.meta.PlayerProfile
import com.rozularen.net.session.GameSession

class GamePlayer(val session: GameSession,
                 private val profile: PlayerProfile,
                 location: Location)
    : BaseEntity(location), Player {

    var name = profile.name

    fun join(session: GameSession) {
        //send initial location from last(¿)
        //TODO: SEND SESSION MESSAGE: session.send()
       // session.send(JoinGameMessage(id))
    }

    override fun pulse() {
        super.pulse()


    }

    fun remove() {

        //TODO: Implement remove
    }

    fun saveData(isAsync: Boolean){
        if(isAsync) {

        } else {
        }
    }
}
