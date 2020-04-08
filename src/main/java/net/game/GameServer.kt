package net.game

import io.netty.channel.Channel
import net.MainServer
import protocol.ProtocolProvider
import java.net.InetSocketAddress
import java.util.concurrent.CountDownLatch
import kotlin.system.exitProcess

class GameServer(server: MainServer, protocolProvider: ProtocolProvider, latch: CountDownLatch)
    : BaseServer(server, protocolProvider, latch) {
    init {
        bootstrap.childHandler(GameServerHandler(this))
    }

    override fun onBindSuccess(address: InetSocketAddress) {
        server.port = address.port
        server.ip = address.hostString
        super.onBindSuccess(address)
    }

    override fun onBindFailure(address: InetSocketAddress?, t: Throwable?) {
        exitProcess(1)
    }

    override fun shutdown() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun newSession(channel: Channel) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        //val session = Session(server, protocolProvider, channel, this)
    }

}