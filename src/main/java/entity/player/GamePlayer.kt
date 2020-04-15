package entity.player

import entity.BaseEntity
import entity.location.Location
import entity.meta.PlayerProfile
import net.message.play.game.JoinGameMessage
import net.session.GameSession
import world.GameWorld

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
