package entity

import entity.player.GamePlayer

class EntityManager {

    private val entities: MutableMap<Int, BaseEntity> = mutableMapOf()

    //private val groupedEntities :Map<>

    fun move() {

    }

    fun get(id: Int): BaseEntity? {
        return entities[id]
    }

    fun getAll(): Collection<BaseEntity> {
        return entities.values
    }

    fun register(entity: BaseEntity) {
        entities[entity.id] = entity
    }

    fun unregister(entity: BaseEntity) {

    }

    fun getAllPayers(): List<GamePlayer> {
        return entities.values
                .filter {
                    it is GamePlayer
                }.map {
                    it as GamePlayer
                }.toList()
    }
}