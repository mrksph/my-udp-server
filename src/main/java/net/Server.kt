package net

import world.GameWorld
import world.WorldCreator
import java.io.File

interface Server {

    //    fun getPlayer(playerId: String)
//    fun getPlayer(playerId: UUID)
    fun addWorld(world: GameWorld)

    fun createWorld(worldCreator: WorldCreator): GameWorld
    fun getWorldContainer(): File
    fun loadConfig()
    fun reload()
    fun shutdown()

}