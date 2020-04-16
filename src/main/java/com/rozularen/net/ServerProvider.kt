package com.rozularen.net

object ServerProvider   {
    lateinit var server: MainServer

    fun registerServer(server: MainServer) {
        ServerProvider.server = server
    }
}