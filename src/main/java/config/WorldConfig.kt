package config

import java.io.File

class WorldConfig(var directory: File, var configFile: File) {

    enum class Key(val path: String, val default: Any) {
        WORLD_COORDINATE_SCALE("world.coordinate-scale", 200),
        WORLD_HEIGHT_SCALE("world.height.scale", 200),
        WORLD_FOLDER("folders.world", "world")
    }
}
