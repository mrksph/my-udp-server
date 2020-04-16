package com.rozularen.io

import com.rozularen.io.service.GameMetadataService
import com.rozularen.world.GameWorld
import java.io.File

//TODO : AQUI VAN LOS SERVICIOS PARA ESCRIBIR EN DISCO
class WorldStorageProvider(private val folder: File) : StorageProvider {
    private lateinit var world: GameWorld

    private var dataDir: File = File(folder, "data")
    lateinit var gameMetadataService: GameMetadataService

    init {
        this.dataDir.mkdirs()
    }

    override fun setWorld(world: GameWorld) {
        this.world = world
        // Will use var folder to create I/O Services
        gameMetadataService = GameMetadataService(world, folder)
        dataDir.mkdirs()
    }


}