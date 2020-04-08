package world

import io.WorldStorageProvider
import net.MainServer
import java.util.*

class World {

    var server: MainServer
    lateinit var name: String
    lateinit var uuid: UUID
    var initialized: Boolean = false

    constructor(
            server: MainServer,
            worldCreator: WorldCreator,
            worldStorageProvider: WorldStorageProvider) {
        this.server = server
        server.addWorld(this)
        initialized = true
    }

    fun pulse() {

    }

}