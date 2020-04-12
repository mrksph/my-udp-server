package scheduler

import world.GameWorld

class WorldEntry(var world: GameWorld) {
    lateinit var task : WorldThread
}