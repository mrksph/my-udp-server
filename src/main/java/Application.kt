import game.MainServer
import kotlin.system.exitProcess

const val DEFAULT_PORT = 31047

fun main(args: Array<String>) {
    try {
        val server = MainServer(args)
        /*
       Instantiate Server from arguments (??)
        */
        server.run()
    } catch (t: Throwable) {
        exitProcess(1)
    }

}