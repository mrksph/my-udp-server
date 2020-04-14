package net.http

import io.netty.bootstrap.ServerBootstrap

interface HttpClient {
//    private val DEFAULT_PORT = 31048
        fun connect();
//    fun bind(port: Int = DEFAULT_PORT) = ServerBootstrap().group()
}