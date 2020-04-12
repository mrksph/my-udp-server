package net

object ServerProvider   {
    lateinit var server: MainServer

    fun registerServer(server: MainServer) {
        this.server = server
    }
}