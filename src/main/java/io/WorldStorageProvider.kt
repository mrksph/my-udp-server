package io

import world.World
import java.io.File

class WorldStorageProvider(private var folder: File) : StorageProvider {
    private lateinit var world: World

    private var dataDir: File = File(folder, "data")

    init {
        this.dataDir.mkdirs()
    }

    override fun setWorld(world: World) {
        this.world = world
        dataDir.mkdirs()
        // Will use var folder to create I/O Services
    }


}