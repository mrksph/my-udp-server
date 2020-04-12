package config

import java.io.File
import java.io.IOException
import java.util.*

class Config(var configDirName: String, var configFilename: String, var parameters: EnumMap<Key, Any>) {

    private val defaultPort = 31047

    private var configDir: File = File("config")
    private var configFile: File = File(configDir, "server-conf.yml")
    private var worldConfig: WorldConfig = WorldConfig(configDir, File(configDir, "world-conf.yml"))

    init {
        load()
    }

    fun fromArgs(args: Array<String>) {
        //Todo: create config from args (args may be -help command)
    }


    fun load() {
        var changed = false

        if (!configFile.exists()) {
            if (!configDir.isDirectory && !configDir.mkdirs()) {
                return
            }
            changed = true
        } else {
            // load config from file
        }

        if (changed) {
            save()
        }
    }

    private fun save() {
        try {
            // save file
        } catch (ex: IOException) {
            System.err.println("Error while saving config")
        }
    }

    fun getString(key: Key): String {
        if (parameters.containsKey(key)) {
            return parameters[key].toString()
        }
        parameters[key] = parameters[key].toString()
        return ""
    }

    fun getInt(serverPort: Key): Int {
        return if (parameters.containsKey(serverPort)) {
            parameters[serverPort] as Int
        } else {
            //TODO: I NEED TO USE A LIBRARY TO READ FROM CONFIG FILE
            //TO LOAD DEFAULT VALUE
            // val integer : Int  = getInt(serverPort.path, serverPort.default)
            0
        }

    }

    enum class Key(val path: String, val default: Any) {
        SERVER_IP("server.ip", "0.0.0.0"),
        SERVER_PORT("server.port", "31049"),
        WORLD_FOLDER("folders.world", "world")
    }
}