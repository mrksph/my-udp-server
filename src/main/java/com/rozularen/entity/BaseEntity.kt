package com.rozularen.entity

import com.rozularen.entity.location.Location
import com.rozularen.net.MainServer
import com.rozularen.world.GameWorld

abstract class BaseEntity(val location: Location) : Entity {

    var id: Int = 0

    private var server: MainServer
    internal var world: GameWorld
    lateinit var origin: Location
    lateinit var prevLocation: Location

    init {
        this.world = location.world
        this.server = world.server


        server.entityIdManager.allocate(this)
        world.entityManager.register(this)
    }

    open fun pulse() {

    }

}