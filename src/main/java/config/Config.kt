package config

import java.io.File

class Config {

    private lateinit var configDir: File
    private lateinit var configFile: File
    private lateinit var worldConfig: File

    fun parseConfig(args: Array<String>): Config {
        val configDirName = "config"
        val configFileName = "server-conf.yml"

        configDir = File(configDirName)
        worldConfig = WorldConfig(configDir, File(configDir,"worlds-conf.yml"))
        configFile = File(configFile, configFileName)
        return Config()
    }

    fun load() {


    }
}