package net.game

import io.netty.bootstrap.Bootstrap
import io.netty.channel.Channel
import io.netty.channel.ChannelFuture
import io.netty.channel.ChannelOption
import io.netty.channel.EventLoopGroup
import io.netty.channel.epoll.Epoll
import io.netty.channel.epoll.EpollDatagramChannel
import io.netty.channel.epoll.EpollEventLoopGroup
import io.netty.channel.epoll.EpollServerSocketChannel
import io.netty.channel.kqueue.KQueue
import io.netty.channel.kqueue.KQueueDatagramChannel
import io.netty.channel.kqueue.KQueueEventLoopGroup
import io.netty.channel.kqueue.KQueueServerSocketChannel
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.DatagramChannel
import io.netty.channel.socket.ServerSocketChannel
import io.netty.channel.socket.nio.NioDatagramChannel
import io.netty.channel.socket.nio.NioServerSocketChannel
import net.MainServer
import net.session.BasicSession
import net.session.Session
import net.session.SessionRegistry
import protocol.ProtocolProvider
import java.net.InetSocketAddress
import java.util.concurrent.CountDownLatch

abstract class BaseServer(var server: MainServer,
                          var protocolProvider: ProtocolProvider,
                          var latch: CountDownLatch) {

    private val EPOLL_AVAILABLE = Epoll.isAvailable()
    private val KQUEUE_AVAILABLE = KQueue.isAvailable()

    private var group: EventLoopGroup

    protected var bootstrap: Bootstrap
    protected val sessions: SessionRegistry = SessionRegistry()

    private lateinit var channel: Channel

    init {
        group = createBestEventLoopGroup()

        bootstrap = Bootstrap()
        //to configure a bootstrap we need a:
        // a channel
        bootstrap.group(group)
                .channel(bestDatagramChannel())
                .option(ChannelOption.SO_KEEPALIVE, true);
    }

    open fun bind(address: InetSocketAddress) {
        val channelFuture = this.bootstrap.bind(address).addListener {
            if (it.isSuccess) {
                onBindSuccess(address)
            } else {
                onBindFailure(address, it.cause())
            }
        }
        channel = channelFuture.channel()
    }

    open fun onBindSuccess(address: InetSocketAddress) {
        latch.countDown()
    }

    abstract fun newSession(channel: Channel): BasicSession

    abstract fun removeSession(session: Session)

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

    private fun bestDatagramChannel(): Class<out DatagramChannel?>? {
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

    fun shutdown() {
        channel.close()
        bootstrap.config().group().shutdownGracefully()

        try {
            bootstrap.config().group().terminationFuture().sync()
        } catch (e: InterruptedException) {
            System.err.println("Datagram server shutdown process interrupted!")
        }
    }

}

