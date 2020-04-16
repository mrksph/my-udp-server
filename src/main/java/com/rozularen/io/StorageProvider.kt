package com.rozularen.io

import com.rozularen.world.GameWorld

interface StorageProvider {
    fun setWorld(world: GameWorld)
}
