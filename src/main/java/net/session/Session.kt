package net.session

import net.message.Message

interface Session {

    fun messageReceived(message: Message)

    fun getProcessor()

    fun getProtocol()

    fun send()

    fun sendAll()

    fun disconnect()

    fun onReady()

    fun onDisconnect()

    fun pulse()


}