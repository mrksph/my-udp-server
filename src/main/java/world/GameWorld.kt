package world

import entity.EntityManager
import entity.location.Location
import generator.WorldGenerator
import io.WorldStorageProvider
import net.MainServer
import java.util.*

class GameWorld(val server: MainServer,
                val worldCreator: WorldCreator,
                val storage: WorldStorageProvider) {

    private var generator: WorldGenerator
    val entityManager: EntityManager = EntityManager()

    lateinit var name: String
    lateinit var uuid: UUID
    var initialized: Boolean = false

    init {
        storage.setWorld(this)
        server.addWorld(this)
        initialized = true
        generator = worldCreator.generator
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