package entity

class EntityIdManager {


    var usedIds: MutableSet<Int> = mutableSetOf()
    var lastId: Int = 0

    @Synchronized
    fun allocate(entity: BaseEntity): Int {
        val startedAt = lastId

        // intentionally wraps around integer boundaries
        for (id in lastId + 1 until startedAt) { // skip special values
            if (id == -1 || id == 0) {
                continue
            }
            if (usedIds.add(id)) {
                entity.id = id
                lastId = id
                return id
            }
        }
        throw IllegalStateException("Cant allocate")
    }

    @Synchronized
    fun deallocate(entity: BaseEntity) {
        usedIds.remove(entity.id)
    }


}