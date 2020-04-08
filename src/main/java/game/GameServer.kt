package game

import io.netty.channel.Channel
import io.netty.channel.ChannelFuture
import java.net.InetSocketAddress
import java.util.concurrent.CountDownLatch
import kotlin.system.exitProcess

class GameServer(latch: CountDownLatch) : BaseServer() {
    init {
        bootstrap.childHandler(GameServerHandler(this))
    }

    override fun bind(address: InetSocketAddress): ChannelFuture {
        //Log something
        return super.bind(address)
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
    }
}