package com.rozularen.net.codec

import com.rozularen.net.message.GameMessage
import io.netty.buffer.ByteBuf

abstract class GameCodec<T : GameMessage> {

    abstract fun encode(buffer: ByteBuf, message: T): ByteBuf

    abstract fun decode(buffer: ByteBuf): T

    abstract fun getCodecName(): String

    class CodecRegistration(private val opcode: Int, private val codec: GameCodec<*>) {

        fun <M : GameMessage> getCodec(): GameCodec<*> {
            return codec
        }

        override fun hashCode(): Int {
            val hash = 5
            return 67 * hash + opcode
        }

        override fun equals(obj: Any?): Boolean {
            return when {
                obj == null -> {
                    false
                }
                this.javaClass != obj.javaClass -> {
                    false
                }
                else -> {
                    val other = obj as CodecRegistration?
                    opcode == other!!.opcode
                }
            }
        }

    }

}
