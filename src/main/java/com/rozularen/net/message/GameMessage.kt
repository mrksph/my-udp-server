package com.rozularen.net.message

import java.io.Serializable

//
//abstract class GameMessage(val name: String) {
//
//}
interface GameMessage : Serializable{

    val name : String

    override fun toString(): String

    override fun equals(other: Any?): Boolean

    override fun hashCode(): Int
}