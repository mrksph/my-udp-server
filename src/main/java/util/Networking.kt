package util

import DEFAULT_PORT
import java.net.InetSocketAddress

object Networking {
    fun getBindAddress(portKey: Int): InetSocketAddress {
        val ip: String = ""
        val port: Int = DEFAULT_PORT
        return if (ip.isEmpty()) {
            InetSocketAddress(port)
        } else {
            InetSocketAddress(ip, port)
        }
    }
}