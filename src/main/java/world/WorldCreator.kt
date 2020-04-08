package world

class WorldCreator private constructor(val name: String?){

    data class Builder(var name: String? = null) {
        fun name(name: String) = apply { this.name = name}
        fun build() = WorldCreator(name)
    }
}