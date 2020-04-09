package net.pipeline

import io.netty.channel.ChannelInitializer
import io.netty.channel.socket.DatagramChannel
import net.MainServer
import net.game.GameServer


class PipelineInitializer(private val connectionManager: GameServer) : ChannelInitializer<DatagramChannel>() {

    companion object {
        /**
         * The time in seconds which are elapsed before a client is disconnected due to a read timeout.
         */
        private const val READ_IDLE_TIMEOUT = 20
        /**
         * The time in seconds which are elapsed before a client is deemed idle due to a write timeout.
         */
        private const val WRITE_IDLE_TIMEOUT = 15
    }

    override fun initChannel(channel: DatagramChannel) {
        val handler = MessageHandler(connectionManager)

        channel
                .pipeline()
                .addLast("handler", handler)
    }

}
