package io

import java.io.File


interface StorageProviderFactory {
    companion object {
        fun createWorldStorageProvider(folder:File, worldName: String?) =
                WorldStorageProvider(File(folder, worldName!!))
    }
}