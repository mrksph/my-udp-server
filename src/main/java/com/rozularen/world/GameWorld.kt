package com.rozularen.world

import com.rozularen.entity.EntityManager
import com.rozularen.event.EventFactory
import com.rozularen.event.world.WorldInitEvent
import com.rozularen.generator.WorldGenerator
import com.rozularen.io.WorldStorageProvider
import com.rozularen.net.MainServer
import java.util.*

class GameWorld(val server: MainServer,
                val worldCreator: WorldCreator,
                val storage: WorldStorageProvider) {

    private var generator: WorldGenerator
    val entityManager: EntityManager = EntityManager()

    var name: String = worldCreator.name!!
    var uuid: UUID = UUID.randomUUID()
    var initialized: Boolean = false

    init {
        //TODO: Read UUID FROM SAVED FILE AND MORE THINGS (CREATE THE ACTUAL 2d WORLD)
        storage.setWorld(this)
        server.addWorld(this)
        initialized = true
        generator = worldCreator.generator
        EventFactory.callEvent(WorldInitEvent(this))
    }

    fun pulse() {
        val allPlayers = entityManager.getAllPayers()

        allPlayers.forEach {
            it.pulse()
        }
    }

    fun save(isAsync: Boolean) {
        writeWorldData(isAsync)
    }

    private fun writeWorldData(isAsync: Boolean) {
        maybeAsync(isAsync, Runnable {
            try {
                storage.gameMetadataService.writeWorldData()
                //Here add more things to save when saving the world data
            } catch (e: Exception) {
                System.err.println("Could not save world")
                e.printStackTrace()
            }
        })
    }

    private fun maybeAsync(isAsync: Boolean, runnable: Runnable) {
        if (isAsync) {
            server.scheduler.runTaskAsync(runnable)
        } else {
            runnable.run()
        }
    }

}