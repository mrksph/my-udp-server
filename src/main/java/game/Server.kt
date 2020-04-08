package game

import java.util.*

interface Server {

    fun getName()
    fun getVersion()
    fun getMaxPlayers()
    fun getPort()
//    fun getPlayer(playerId: String)
//    fun getPlayer(playerId: UUID)

}