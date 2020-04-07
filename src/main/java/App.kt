import game.GameServer
import sample.GameClient

fun main() {
//        println("Binding HTTP Server")
//        HttpServer.bind()
    println("Binding Game Server...")
    GameServer.bind()
    GameClient.connect()

    println("Done.")
}
