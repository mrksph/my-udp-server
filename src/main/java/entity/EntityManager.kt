package entity

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
        entities.put(entity.id, entity)
    }

    fun unregister(entity: BaseEntity) {

    }
}