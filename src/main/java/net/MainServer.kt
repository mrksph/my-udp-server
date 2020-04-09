package net

import DEFAULT_PORT
import config.Config
import io.StorageProviderFactory
import io.WorldStorageProvider
import io.netty.channel.EventLoopGroup
import io.netty.channel.socket.DatagramChannel
import net.game.GameServer
import protocol.ProtocolProvider
import scheduler.ServerScheduler
import scheduler.WorldScheduler
import util.Networking
import util.ThreadKiller
import world.World
import world.WorldCreator
import java.io.File
import java.net.InetSocketAddress
import java.util.concurrent.CountDownLatch
import kotlin.system.exitProcess

class MainServer(config: Array<String>) : Server {

    private var config: Config

    private lateinit var channel: Class<out DatagramChannel?>
    private lateinit var eventLoopGroup: EventLoopGroup
    private lateinit var storageProvider: WorldStorageProvider
    private lateinit var worldContainer: File
    private lateinit var worldFolder: String
    private lateinit var gameServer: GameServer

    private val worlds: WorldScheduler = WorldScheduler()
    private val scheduler: ServerScheduler = ServerScheduler(this, worlds)
    private var isShuttingDown: Boolean = false

    var port = 0
    var ip: String? = null

    init {
        this.config = Config().parseConfig(config)
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

        // Set port and ip from config

        //2. Create world
        val worldCreator = WorldCreator.Builder()
                .name("Hola")
                .build()

        createWorld(worldCreator)

        //3. Finally start scheduler (tick, etc)
        scheduler.start()
    }

    fun bind() {
        //CountDownLatch (??)
        val latch = CountDownLatch(1)
        //Create a GameServer (main server)
        val protocolProvider = ProtocolProvider()

        gameServer = GameServer(this, protocolProvider, latch)
        gameServer.bind(Networking.getBindAddress(DEFAULT_PORT))
        //Create NetworkServer (its a type of GameServer)
        //Create a RemoteConsoleServer (remote console protocol)
        try {
            latch.await()
        } catch (exception: InterruptedException) {
            exitProcess(1)
        }
    }


    private fun onBindSuccess(address: InetSocketAddress) {
        ip = address.hostString
        port = address.port
    }

    private fun onBindFailure(address: InetSocketAddress, cause: Throwable) {
        System.err.println("Error while binding $address")
        when {
            cause.message!!.contains("Cannot assign requested address") -> {
                System.err.println("The 'server.ip' in your configuration may not be valid.")
            }
            cause.message!!.contains("Address already in use") -> {
                System.err.println("The address was already in use. Check that no server is")
                System.err.println("already running on that port. If needed, try killing all")
                System.err.println("Java processes using Task Manager or similar.")
            }
            else -> {
                System.err.println("An unknown bind error has occurred.")
            }
        }
        exitProcess(1)
    }

    override fun getName() {
    }

    override fun getVersion() {
    }


    override fun addWorld(world: World) {}

    override fun createWorld(worldCreator: WorldCreator): World =
            World(
                    this,
                    worldCreator,
                    StorageProviderFactory.createWorldStorageProvider(worldContainer, worldCreator.name)
            )

    override fun getWorldContainer(): File = File(worldFolder)

    override fun loadConfig() {
        config.load()
    }

    override fun shutdown() {
        if (isShuttingDown) {
            return
        }
        isShuttingDown = true

        //todo: iterate players list and kick them
        //stops network servers
        gameServer.shutdown()
        //save world

        scheduler.stop()
        ThreadKiller().start()
    }

}

