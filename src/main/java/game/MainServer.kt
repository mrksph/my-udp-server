package game

import DEFAULT_PORT
import config.Config
import io.netty.bootstrap.Bootstrap
import io.netty.channel.ChannelFuture
import io.netty.channel.EventLoopGroup
import io.netty.channel.epoll.Epoll
import io.netty.channel.epoll.EpollDatagramChannel
import io.netty.channel.epoll.EpollEventLoopGroup
import io.netty.channel.kqueue.KQueue
import io.netty.channel.kqueue.KQueueDatagramChannel
import io.netty.channel.kqueue.KQueueEventLoopGroup
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.DatagramChannel
import io.netty.channel.socket.nio.NioDatagramChannel
import util.Networking
import java.net.InetSocketAddress
import java.util.concurrent.CountDownLatch
import kotlin.system.exitProcess

class MainServer(config: Array<String>) : Server {

    private var config: Config

    private val server = Bootstrap()
    private lateinit var channel: Class<out DatagramChannel?>
    private lateinit var eventLoopGroup: EventLoopGroup
    var port = 0
    var ip: String? = null

    init {
        this.config = Config().parseConfig(config)
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
        //2. Create world
        createWorld()
        //3. Finally start scheduler (tick, etc)
    }

    private fun bind() {
        //CountDownLatch (??)
        val latch = CountDownLatch(1)
        //Create a GameServer (main server)
        prepareGameServer(latch)
        //Create NetworkServer (its a type of GameServer)
        //Create a RemoteConsoleServer (remote console protocol)
        bind(Networking.getBindAddress(DEFAULT_PORT))
        try {
            latch.await()
        } catch (exception: InterruptedException) {
            exitProcess(1)
        }
    }

    private fun createWorld() {


    }

    private fun prepareGameServer(latch: CountDownLatch) {
        val gameServer = GameServer(latch)

    }

    fun bind(address: InetSocketAddress): ChannelFuture {
        return server.bind(address).addListener {
            if (it.isSuccess) {
                onBindSuccess(address)
            } else {
                onBindFailure(address, it.cause())
            }
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


}

