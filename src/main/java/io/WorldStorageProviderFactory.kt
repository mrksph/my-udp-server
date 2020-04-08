package io

import world.WorldStorageProvider

interface WorldStorageProviderFactory {
    fun createWorldStorageProvider(worldName: String?): WorldStorageProvider
}