package net.game

import io.netty.channel.Channel
import net.MainServer
import net.session.BasicSession
import net.session.Session
import net.session.SessionRegistry
import protocol.ProtocolProvider
import java.net.InetSocketAddress
import java.util.concurrent.CountDownLatch
import kotlin.system.exitProcess

class GameServer(server: MainServer,
                 protocolProvider: ProtocolProvider,
                 latch: CountDownLatch)
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

    override  fun newSession(channel: Channel): BasicSession {
        val session = BasicSession(server, protocolProvider, channel, this)
        sessions.add(session)
        return session
    }

    override fun removeSession(session: Session) {
        sessions.remove(session)
    }
}