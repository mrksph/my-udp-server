package com.rozularen.net.game

import com.rozularen.net.MainServer
import com.rozularen.net.pipeline.GameChannelInitializer
import com.rozularen.net.protocol.ProtocolProvider
import com.rozularen.net.protocol.play.PlayProtocol
import com.rozularen.net.session.BaseSession
import com.rozularen.net.session.GameSession
import io.netty.channel.Channel
import java.net.InetSocketAddress
import java.util.concurrent.CountDownLatch
import kotlin.system.exitProcess

class GameServer(server: MainServer,
                 protocolProvider: ProtocolProvider,
                 latch: CountDownLatch)
    : BaseServer(server, protocolProvider, latch) {

    init {
        bootstrap.handler(GameChannelInitializer(this))
    }

    override fun onBindSuccess(address: InetSocketAddress) {
        server.port = address.port
        server.ip = address.hostString
        println("ONBIND SUCCESS IP:${server.ip} PORT:${server.port}")
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

    fun sessionInactivated(gameSession: GameSession) {
        server.sessionRegistry.remove(gameSession)
    }

}