package com.rozularen.entity

class EntityIdManager {


    var usedIds: MutableSet<Int> = mutableSetOf()
    var lastId: Int = 0

    @Synchronized
    fun allocate(entity: BaseEntity): Int {
        // TODO: WATCH THIS OUT MAY OCCUR SOME PROBLEM
        val id = lastId + 1

        if (usedIds.add(id)) {
            entity.id = id
            lastId = id
            return id
        }

        throw IllegalStateException("Cant allocate")
    }

    @Synchronized
    fun deallocate(entity: BaseEntity) {
        usedIds.remove(entity.id)
    }


}