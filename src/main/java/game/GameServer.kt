package game

import DEFAULT_PORT
import io.netty.bootstrap.Bootstrap
import io.netty.channel.ChannelFuture
import io.netty.channel.ChannelOption
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
import kotlin.system.exitProcess

object GameServer {
    private val EPOLL_AVAILABLE = Epoll.isAvailable()
    private val KQUEUE_AVAILABLE = KQueue.isAvailable()
    private val server = Bootstrap()
    private lateinit var channel : Class<out DatagramChannel?>
    private lateinit var eventLoopGroup : EventLoopGroup

    /**
     * The server port.
     */
    private var port = 0
    /**
     * The server ip.
     */
    private var ip: String? = null

    @JvmStatic
    fun main(args: Array<String>) {
        println("Setting up Game Server (Port: $DEFAULT_PORT)")
        start()
    }

    /**
     * The idea is to first create the world in the server then bind the server to receive connections
     * In the first step we load all the data and configuration
     * In the second step we then start everything we need to accept client requests
     * See Glowstone's GlowServer class for more info
     */
    private fun start() {
        try {
            prepareServer()
            println("Game Server created successfully")

            bind(Networking.getBindAddress(DEFAULT_PORT))
            println("After closing")
        } finally {
            println("Disconnecting Game Server...")
            eventLoopGroup.shutdownGracefully()
            println("Game Server disconnected successfully")
        }
    }

    private fun prepareServer() {
        channel = bestDatagramChannel()
        eventLoopGroup = getBestEventLoopGroup()

        server.group(eventLoopGroup)
                .channel(channel)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .handler(GameServerHandler)
    }

    private fun bind(address: InetSocketAddress): ChannelFuture {
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

    private fun getBestEventLoopGroup(): EventLoopGroup {
        return when {
            EPOLL_AVAILABLE -> {
                EpollEventLoopGroup()
            }
            KQUEUE_AVAILABLE -> {
                KQueueEventLoopGroup()
            }
            else -> {
                NioEventLoopGroup()
            }
        }
    }

    private fun bestDatagramChannel(): Class<out DatagramChannel?> {
        return when {
            EPOLL_AVAILABLE -> {
                EpollDatagramChannel::class.java
            }
            KQUEUE_AVAILABLE -> {
                KQueueDatagramChannel::class.java
            }
            else -> {
                NioDatagramChannel::class.java
            }
        }
    }

}

