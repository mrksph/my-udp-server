package entity.player

import entity.BaseEntity
import entity.location.Location
import entity.meta.PlayerProfile
import net.session.GameSession
import world.GameWorld

class GamePlayer(val session: GameSession,
                 val profile: PlayerProfile,
                 location: Location)
    : BaseEntity(getLocation(session)), Player {

    var name = profile.name

    fun join(session: GameSession) {

        //send initial location from last(¿)
        //TODO: SEND SESSION MESSAGE: session.send()
    }

    override fun pulse() {
        super.pulse()


    }

    fun remove() {

        //TODO: Implement remove
    }
}
