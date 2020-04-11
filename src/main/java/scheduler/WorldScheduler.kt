package scheduler

import world.World
import java.util.concurrent.CopyOnWriteArrayList
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.Phaser

class WorldScheduler {

    val advancedCondition: Object = Object()
    val worldExecutor: ExecutorService = Executors.newCachedThreadPool()
    val worlds: List<WorldEntry> = CopyOnWriteArrayList()
    val tickBegin: Phaser = Phaser(1)
    val tickEnd: Phaser = Phaser(1)

    @Volatile
    private var currentTick: Int = -1

    fun addWorld(world: World) {

    }

    fun removeWorld(world: World) {

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