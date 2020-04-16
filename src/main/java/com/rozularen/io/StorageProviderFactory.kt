package com.rozularen.io

import java.io.File


interface StorageProviderFactory {
    companion object {
        fun createStorageProvider(folder: File, worldName: String?) =
                WorldStorageProvider(File(folder, worldName!!))
    }
}