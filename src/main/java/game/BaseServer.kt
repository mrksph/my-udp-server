package game

import io.netty.bootstrap.ServerBootstrap
import io.netty.channel.Channel
import io.netty.channel.ChannelFuture
import io.netty.channel.ChannelOption
import io.netty.channel.EventLoopGroup
import io.netty.channel.epoll.Epoll
import io.netty.channel.epoll.EpollEventLoopGroup
import io.netty.channel.epoll.EpollServerSocketChannel
import io.netty.channel.kqueue.KQueue
import io.netty.channel.kqueue.KQueueEventLoopGroup
import io.netty.channel.kqueue.KQueueServerSocketChannel
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.ServerSocketChannel
import io.netty.channel.socket.nio.NioServerSocketChannel
import protocol.ProtocolProvider
import java.net.InetSocketAddress
import java.util.concurrent.CountDownLatch

abstract class BaseServer(var server: MainServer,
                          var protocolProvider: ProtocolProvider,
                          var latch: CountDownLatch) {

    private val EPOLL_AVAILABLE = Epoll.isAvailable()
    private val KQUEUE_AVAILABLE = KQueue.isAvailable()

    protected var bossGroup: EventLoopGroup
    protected var workerGroup: EventLoopGroup
    protected var bootstrap: ServerBootstrap

    lateinit var channel: Channel

    init {
        bossGroup = createBestEventLoopGroup()
        workerGroup = createBestEventLoopGroup()
        bootstrap = ServerBootstrap()

        bootstrap.group(bossGroup, workerGroup)
                .channel(bestServerSocketChannel())
                .childOption(ChannelOption.TCP_NODELAY, true)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
    }

    open fun bind(address: InetSocketAddress): ChannelFuture {
        val channelFuture = this.bootstrap.bind(address).addListener {
            if (it.isSuccess) {
                onBindSuccess(address)
            } else {
                onBindFailure(address, it.cause())
            }
        }
        channel = channelFuture.channel()
        return channelFuture
    }

    open fun onBindSuccess(address: InetSocketAddress) {
        latch.countDown()
    }

    abstract fun newSession(channel: Channel)

    abstract fun onBindFailure(address: InetSocketAddress?, t: Throwable?)

    private fun createBestEventLoopGroup(): EventLoopGroup {
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

    /**
     * Gets the "best" server socket channel available.
     *
     *
     * Epoll and KQueue are favoured and will be returned if available, followed by NIO.
     *
     * @return the "best" server socket channel available
     */
    private fun bestServerSocketChannel(): Class<out ServerSocketChannel?>? {
        return when {
            EPOLL_AVAILABLE -> {
                EpollServerSocketChannel::class.java
            }
            KQUEUE_AVAILABLE -> {
                KQueueServerSocketChannel::class.java
            }
            else -> {
                NioServerSocketChannel::class.java
            }
        }
    }

    abstract fun shutdown()

}

