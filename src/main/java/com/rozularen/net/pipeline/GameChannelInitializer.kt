package com.rozularen.net.pipeline

import com.rozularen.net.game.GameServer
import com.rozularen.net.protocol.ProtocolProvider
import io.netty.channel.ChannelInitializer
import io.netty.channel.socket.DatagramChannel

class GameChannelInitializer(private var connectionManager: GameServer) : ChannelInitializer<DatagramChannel>() {

    override fun initChannel(channel: DatagramChannel) {
        // Tell the pipeline to run MyBusinessLogicHandler's event handler methods
        // in a different thread than an I/O thread so that the I/O thread is not blocked by
        // a time-consuming task.
        // If your business logic is fully asynchronous or finished very quickly, you don't
        // need to specify a group.

        val codecsHandler = CodecsHandler(ProtocolProvider.HANDSHAKE)
        channel.pipeline()
                .addLast("codecs", codecsHandler)
                .addLast("messages", MessagesHandler(connectionManager))
        //   .addLast("logic", MyLogicHandler())
        //   .pipeline.addLast(group, "handler", new MyBusinessLogicHandler());
    }

}