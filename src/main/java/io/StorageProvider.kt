package io

import world.GameWorld

interface StorageProvider {
    fun setWorld(world: GameWorld)
}
