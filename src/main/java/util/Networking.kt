package util

import java.net.InetSocketAddress

object Networking {
    fun getBindAddress(portKey: Int): InetSocketAddress {
        val ip: String = ""
        val port: Int = 22222
        return if (ip.isEmpty()) {
            InetSocketAddress(port)
        } else {
            InetSocketAddress(ip, port)
        }
    }
}