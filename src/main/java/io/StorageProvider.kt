package io

import world.World

interface StorageProvider {
    fun setWorld(world: World)
}
