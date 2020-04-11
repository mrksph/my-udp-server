package net.codec

import io.netty.buffer.ByteBuf
import net.message.GameMessage

interface GameCodec<T : GameMessage> {

    fun encode(var1: ByteBuf): T

    fun decode(var1: ByteBuf, var2: T): ByteBuf

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
