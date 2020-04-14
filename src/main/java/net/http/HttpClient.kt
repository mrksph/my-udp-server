package net.http

import io.netty.bootstrap.ServerBootstrap

class HttpClient {
    private val DEFAULT_PORT = 31048

    fun bind(port: Int = DEFAULT_PORT) = ServerBootstrap().group()
}