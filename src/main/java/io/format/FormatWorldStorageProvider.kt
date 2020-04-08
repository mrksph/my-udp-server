package io.format

import io.WorldStorageProviderFactory
import world.World
import world.WorldStorageProvider
import java.io.File

class FormatWorldStorageProvider(file: File) : WorldStorageProvider, WorldStorageProviderFactory {

    private lateinit var world: World

    lateinit var folder: File
    var dataDir: File

    init {
        this.dataDir = File(folder, "data")
        this.dataDir.mkdirs()
    }

    override fun setWorld(world: World) {
        this.world = world
        dataDir.mkdirs()
    }

    override fun createWorldStorageProvider(worldName: String?): WorldStorageProvider {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}