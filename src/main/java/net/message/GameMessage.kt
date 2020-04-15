package net.message
//
//abstract class GameMessage(val name: String) {
//
//}
interface GameMessage {
    override fun toString(): String

    override fun equals(other: Any?): Boolean

    override fun hashCode(): Int
}