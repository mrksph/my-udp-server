package entity

import entity.location.Location
import net.MainServer
import world.GameWorld

abstract class BaseEntity(val location: Location) : Entity {

    var id: Int = 0

    private var server: MainServer
    private var world: GameWorld
    lateinit var origin: Location
    lateinit var prevLocation: Location

    init {
        this.world = location.world
        this.server = world.server


        server.entityIdManager.allocate(this)
        world.entityManager.register(this)
    }

}