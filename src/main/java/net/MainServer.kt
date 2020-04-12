package net

import DEFAULT_PORT
import config.Config
import entity.EntityIdManager
import entity.EntityManager
import io.StorageProviderFactory
import net.game.GameServer
import net.protocol.ProtocolProvider
import net.session.SessionRegistry
import scheduler.ServerScheduler
import scheduler.WorldScheduler
import util.Networking
import util.ThreadKiller
import world.GameWorld
import world.WorldCreator
import java.io.File
import java.util.*
import java.util.concurrent.CountDownLatch
import kotlin.system.exitProcess

class MainServer(args: Array<String>) : Server {

    /**
     * Load Configuration when MainServer is instantiated
     */
    private var config: Config =
            Config(
                    "config",
                    "server-conf.yml",
                    EnumMap(Config.Key::class.java)
            )

    private lateinit var worldFolder: String
    private lateinit var server: GameServer
    private var isShuttingDown: Boolean = false

    private val worldContainer: File = File(config.getString(Config.Key.WORLD_FOLDER))
    val sessionRegistry: SessionRegistry = SessionRegistry()
    private val worlds: WorldScheduler = WorldScheduler()
    val scheduler: ServerScheduler = ServerScheduler(this, worlds)

    val entityIdManager : EntityIdManager = EntityIdManager()
    val entityManager : EntityManager = EntityManager()

    var port = 0
    var ip: String? = null
    val version: String = ""


    init {
        loadConfig()
    }

    /**
     * The idea is to first create the world in the server then bind the server to receive connections
     * In the first step we load all the data and configuration
     * In the second step we then start everything we need to accept client requests
     * See Glowstone's GlowServer class for more info
     */
    fun run() {
        start()
        bind()
    }

    private fun start() {
        // 1. Load players info saved

        // Get port and ip from config
        this.ip = config.getString(Config.Key.SERVER_IP)
        this.port = config.getInt(Config.Key.SERVER_PORT)

        //2. Create world (also create a seed and define a type)
        val worldCreator = WorldCreator.Builder()
                .name("world-1")
                .build()

        createWorld(worldCreator)

        //3. Finally start scheduler (tick, etc)
        scheduler.start()
    }

    fun bind() {
        val latch = CountDownLatch(1)
        val protocolProvider = ProtocolProvider()
        val address = Networking.getBindAddress(DEFAULT_PORT)

        //Create a GameServer (main server)
        server = GameServer(this, protocolProvider, latch)
        println("Binding Main Server... addres: $address")
        server.bind(address)

        //Create NetworkServer (its a type of GameServer)
        //Create a RemoteConsoleServer (remote console protocol)
        try {
            latch.await()
        } catch (exception: InterruptedException) {
            exitProcess(1)
        }
    }



    override fun addWorld(world: GameWorld) {}

    override fun createWorld(worldCreator: WorldCreator): GameWorld =
            GameWorld(
                    this,
                    worldCreator,
                    StorageProviderFactory.createWorldStorageProvider(worldContainer, worldCreator.name)
            )

    override fun getWorldContainer(): File = File(worldFolder)

    override fun loadConfig() {
        config.load()
    }

    override fun reload() {
        TODO("Reload configuration")
    }

    override fun shutdown() {
        if (isShuttingDown) {
            return
        }
        isShuttingDown = true

        //todo: iterate players list and kick them
        //stops network servers
        server.shutdown()
        //save world

        scheduler.stop()
        ThreadKiller.start()
    }

}

