package scheduler

import world.GameWorld
import java.util.*
import java.util.concurrent.CopyOnWriteArrayList
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.Phaser

class WorldScheduler {

    private val advancedCondition: Object = Object()
    private val worldExecutor: ExecutorService = Executors.newCachedThreadPool()
    private var worlds: MutableList<WorldEntry> = CopyOnWriteArrayList()
    private val tickBegin: Phaser = Phaser(1)
    private val tickEnd: Phaser = Phaser(1)

    @Volatile
    private var currentTick: Int = -1


    fun getWorld(name: String) : GameWorld? {
        val find = worlds.find { it.world.name == name }
        return find?.world
    }

    fun getWorld(uuid: UUID) : GameWorld? {
        val find = worlds.find { it.world.uuid == uuid }
        return find?.world
    }

    fun addWorld(world: GameWorld): GameWorld? {
        val worldEntry = WorldEntry(world)
        worlds.add(worldEntry)

        return try {
            worldEntry.task = WorldThread(world, tickBegin, tickEnd)
            tickBegin.register()
            tickEnd.register()
            worldExecutor.submit(worldEntry.task)
            world
        } catch (t: Throwable) {
            tickBegin.arriveAndDeregister()
            tickEnd.arriveAndDeregister()
            worlds.remove(worldEntry)
            null
        }
    }

    fun removeWorld(world: GameWorld): Boolean {
        worlds.forEach{
            if(it.world == world) {
                it.task.interrupt()
            }
            worlds.remove(it)
            return true
        }
        return false
    }

    fun beginTick(): Int {
        tickEnd.awaitAdvanceInterruptibly(currentTick) // Make sure previous tick is complete
        return tickBegin.arrive().also { currentTick = it }
    }

    fun isTickComplete(tick: Int): Boolean {
        return tickEnd.phase > tick || tickEnd.phase < 0
    }

    fun doTickEnd() {
        val currentTick = this.currentTick
        val endPhase = tickEnd.arriveAndAwaitAdvance()
        if (endPhase != currentTick + 1) {
            System.err.println("Tick barrier")
        }
        synchronized(advancedCondition) {
            advancedCondition.notifyAll()
        }
    }

    fun stop() {
        tickBegin.forceTermination()
        tickEnd.forceTermination()
        worldExecutor.shutdownNow()
    }
}