package net.game

import io.netty.channel.Channel
import net.MainServer
import net.session.GameSession
import net.session.BaseSession
import net.protocol.play.PlayProtocol
import protocol.ProtocolProvider
import java.net.InetSocketAddress
import java.util.concurrent.CountDownLatch
import kotlin.system.exitProcess

class GameServer(server: MainServer,
                 protocolProvider: ProtocolProvider,
                 latch: CountDownLatch)
    : BaseServer(server, protocolProvider, latch) {

    init {
        bootstrap.handler(GameServerChannelStarter(this))
    }

    override fun onBindSuccess(address: InetSocketAddress) {
        server.port = address.port
        server.ip = address.hostString
        super.onBindSuccess(address)
    }

    override fun onBindFailure(address: InetSocketAddress, t: Throwable) {
        t.printStackTrace()
        exitProcess(1)
    }

    override fun newSession(channel: Channel): GameSession {
        val session = GameSession(server, PlayProtocol(), channel, this)
        sessions.add(session)
        return session
    }

    override fun removeSession(baseSession: BaseSession) {
        sessions.remove(baseSession)
    }
}