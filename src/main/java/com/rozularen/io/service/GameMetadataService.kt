package com.rozularen.io.service

import com.rozularen.world.GameWorld
import java.io.File
import java.util.*

class GameMetadataService(val world: GameWorld, val folder: File) {

    data class GameFinalValues(val seed: Long, val uuid: UUID)

    fun readWorldData(): GameFinalValues {
        //TODO: Read file from folder with name (set in settings)
        // and then retrieve the values
        return GameFinalValues(0L, UUID.randomUUID())
    }

    fun writeWorldData() {
        //TODO : Write in (MESSAGE Type) to file in folder

    }
}
