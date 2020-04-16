package com.rozularen.scheduler

import com.rozularen.world.GameWorld
import java.util.concurrent.Phaser

class WorldThread(
        private val world: GameWorld,
        private val tickBegin: Phaser,
        private val tickEnd: Phaser)
    : Thread("world-" + world.name) {

    override fun run() {
        try {
            while (!isInterrupted && !tickEnd.isTerminated) {
                tickBegin.arriveAndAwaitAdvance()
                try {
                    world.pulse()
                } catch (e: Exception) {
                    System.err.println("Error occurred while pulsing world ${world.name}")
                    e.printStackTrace()
                } finally {
                    tickEnd.arriveAndAwaitAdvance()
                }
            }
        } finally {
            tickBegin.arriveAndDeregister()
            tickEnd.arriveAndDeregister()
        }
    }

}