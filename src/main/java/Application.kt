import client.GameClient
import net.MainServer
import kotlin.system.exitProcess

const val DEFAULT_PORT = 31047

fun main(args: Array<String>) {
    try {
        val server = MainServer(args)
        server.run()

    } catch (e: Exception) {
        e.printStackTrace()
        exitProcess(1)
    }

}