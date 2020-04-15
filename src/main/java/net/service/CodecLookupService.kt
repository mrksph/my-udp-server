package net.service

import net.codec.GameCodec
import net.message.GameMessage
import java.lang.reflect.InvocationTargetException
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap
import java.util.concurrent.atomic.AtomicInteger

class CodecLookupService(size: Int) {

    private var messages: ConcurrentMap<GameMessage, GameCodec.CodecRegistration>
    private var opcodes: ConcurrentMap<Int, GameCodec<*>>
    private var nextId: AtomicInteger

    init {
        if (size < 0) {
            throw IllegalArgumentException("Size cannot be < 0!")
        } else {
            this.messages = ConcurrentHashMap()
            this.opcodes = ConcurrentHashMap<Int, GameCodec<*>>()
            this.nextId = AtomicInteger(0)
        }
    }

    @Suppress("UNCHECKED_CAST")
    @Throws(InstantiationException::class,
            IllegalAccessException::class,
            InvocationTargetException::class)
    //TODO: CHANGE OPCODE: INT TO INT? POR SI LLEGAN ERRORES EN PAQUETES (?))
    fun bind(message: GameMessage, codec: GameCodec<*>, opcode: Int): GameCodec.CodecRegistration? {
        var codecRegistration = messages[message]
        if (codecRegistration != null) {
            return codecRegistration
        } else {

            require(opcode >= 0) { "Opcode must either be null or greater than or equal to 0!" }

            val previous: GameCodec<*>? = opcodes[opcode]

            return if (previous != null && previous.javaClass !== codec) {
                val s = "Trying to bind an opcode where one already exists. New: ${codec.getCodecName()} Old: ${previous.getCodecName()}"
                throw IllegalStateException(s)
            } else {
                opcodes[opcode] = codec
                codecRegistration = GameCodec.CodecRegistration(opcode, codec)
                messages[message] = codecRegistration
                codecRegistration
            }
        }
    }

    fun find(opcode: Int): GameCodec<*> {
        return opcodes[opcode]!!
    }

    fun find(message: GameMessage): GameCodec.CodecRegistration? {
        return messages[message]
    }

    override fun toString(): String {
        return "CodecLookupService{messages=${messages}, opcodes=${opcodes}}"
    }
}
