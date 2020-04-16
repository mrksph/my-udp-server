package com.rozularen.world

import com.rozularen.generator.WorldGenerator

class WorldCreator private constructor(val name: String?, val generator: WorldGenerator) {

    data class Builder(var name: String? = null, var generator: WorldGenerator = WorldGenerator()) {
        fun name(name: String) = apply { this.name = name }
        fun generator(generator: WorldGenerator) = apply { this.generator = generator }
        fun build() = WorldCreator(name, generator)
    }
}