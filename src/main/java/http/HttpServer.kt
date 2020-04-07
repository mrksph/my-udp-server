package com.pingpong.net.http

import io.netty.bootstrap.ServerBootstrap

object HttpServer {
    const val DEFAULT_PORT = 31048

    fun bind(port: Int = DEFAULT_PORT) = ServerBootstrap().group()
}