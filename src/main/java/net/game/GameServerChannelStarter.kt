package net.game

import io.netty.channel.ChannelInitializer
import io.netty.channel.socket.DatagramChannel

class GameServerChannelStarter(var connectionManager: GameServer) : ChannelInitializer<DatagramChannel>() {

    override fun initChannel(p0: DatagramChannel) {
        // Tell the pipeline to run MyBusinessLogicHandler's event handler methods
        // in a different thread than an I/O thread so that the I/O thread is not blocked by
        // a time-consuming task.
        // If your business logic is fully asynchronous or finished very quickly, you don't
        // need to specify a group.

        p0.pipeline()
                .addLast("codec-handler", CodecsHandler(connectionManager.protocolProvider.HANDSHAKE))
                .addLast("message-handler", MessageHandler(connectionManager))
        //   .addLast("logic", MyLogicHandler())
        //   .pipeline.addLast(group, "handler", new MyBusinessLogicHandler());
    }

}