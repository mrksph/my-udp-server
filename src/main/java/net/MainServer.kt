package net

import DEFAULT_PORT
import config.Config
import io.WorldStorageProviderFactory
import io.format.FormatWorldStorageProvider
import io.netty.bootstrap.Bootstrap
import io.netty.channel.EventLoopGroup
import io.netty.channel.socket.DatagramChannel
import net.game.GameServer
import net.session.SessionRegistry
import protocol.ProtocolProvider
import scheduler.ServerScheduler
import scheduler.WorldScheduler
import util.Networking
import world.World
import world.WorldCreator
import java.io.File
import java.net.InetSocketAddress
import java.util.concurrent.CountDownLatch
import kotlin.system.exitProcess

class MainServer(config: Array<String>) : Server {

    private var config: Config = Config().parseConfig(config)

    private val server = Bootstrap()
    private lateinit var channel: Class<out DatagramChannel?>
    private lateinit var eventLoopGroup: EventLoopGroup
    private var storageProviderFactory: WorldStorageProviderFactory? = null

    val worlds: WorldScheduler = WorldScheduler()
    val scheduler: ServerScheduler = ServerScheduler(this, worlds)

    var port = 0
    var ip: String? = null

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

        // create storage provider
        if (storageProviderFactory == null) {
            storageProviderFactory = FormatWorldStorageProvider(File("s", "s"))
        }
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

        val gameServer = GameServer(this, protocolProvider, latch)
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

    override fun getMaxPlayers() {
    }

    override fun getPort() {
    }

    override fun addWorld(world: World) {}

    override fun createWorld(worldCreator: WorldCreator): World {
        val world = World(this,
                worldCreator,
                storageProviderFactory?.createWorldStorageProvider(worldCreator.name)
        )
        return world
    }

    override fun getWorldContainer(): File {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun getSessionRegistry(): SessionRegistry {
        return SessionRegistry()
    }


}

