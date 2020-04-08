package net

import world.World
import world.WorldCreator
import java.io.File

interface Server {

    fun getName()
    fun getVersion()
    fun getMaxPlayers()
    fun getPort()
//    fun getPlayer(playerId: String)
//    fun getPlayer(playerId: UUID)
    fun addWorld(world: World)
    fun createWorld(worldCreator: WorldCreator): World
    fun getWorldContainer(): File

}