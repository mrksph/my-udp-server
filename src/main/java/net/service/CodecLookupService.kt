package net.service

import net.codec.GameCodec
import net.message.GameMessage
import java.lang.reflect.Constructor
import java.lang.reflect.InvocationTargetException
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap
import java.util.concurrent.atomic.AtomicInteger

class CodecLookupService(size: Int) {

    private var messages: ConcurrentMap<Class<in GameMessage>, GameCodec.CodecRegistration>
    private var opcodes: ConcurrentMap<Int, GameCodec<GameMessage>>
    private var nextId: AtomicInteger

    init {
        if (size < 0) {
            throw IllegalArgumentException("Size cannot be < 0!")
        } else {
            this.messages = ConcurrentHashMap()
            this.opcodes = ConcurrentHashMap<Int, GameCodec<GameMessage>>()
            this.nextId = AtomicInteger(0)
        }
    }

    @Suppress("UNCHECKED_CAST")
    @Throws(InstantiationException::class,
            IllegalAccessException::class,
            InvocationTargetException::class)
    fun    //TODO: CHANGE OPCODE: INT TO INT? POR SI LLEGAN ERRORES EN PAQUETES (?))
            bind(messageClass: Class<in GameMessage>, codecClass: Class<in GameCodec<in GameMessage>>, opcode: Int): GameCodec.CodecRegistration? {
        var reg = messages[messageClass]
        if (reg != null) {
            return reg
        } else {
            val codec: GameCodec<GameMessage>
            try {
                val con: Constructor<in GameCodec<in GameMessage>> = codecClass.getConstructor()
                con.isAccessible = true
                codec = con.newInstance() as GameCodec<GameMessage>
            } catch (var8: IllegalAccessException) {
                throw java.lang.IllegalArgumentException("Codec could not be created!", var8)
            } catch (var8: InvocationTargetException) {
                throw java.lang.IllegalArgumentException("Codec could not be created!", var8)
            } catch (var8: NoSuchMethodException) {
                throw java.lang.IllegalArgumentException("Codec could not be created!", var8)
            }

            var finalOpcode = opcode

            if (opcode != null) {
                require(opcode >= 0) { "Opcode must either be null or greater than or equal to 0!" }
            } else {
                var id: Int
                try {
                    do {
                        id = nextId.getAndIncrement()
                    } while (this.get(id) != null)
                } catch (var9: IndexOutOfBoundsException) {
                    throw IllegalStateException("Ran out of Ids!", var9)
                }
                finalOpcode = id
            }

            val previous: GameCodec<GameMessage>? = this.get(finalOpcode)
            return if (previous != null && previous.javaClass !== codecClass) {
                val s = "Trying to bind an opcode where one already exists. New: ${codecClass.simpleName} Old: ${previous.javaClass.simpleName}"
                throw IllegalStateException(s)
            } else {
                this.put(finalOpcode, codec)
                reg = GameCodec.CodecRegistration(finalOpcode, codec)
                messages[messageClass] = reg
                reg
            }
        }
    }

    private fun get(opcode: Int): GameCodec<GameMessage>? = opcodes[opcode]

    private fun put(opcode: Int, codec: GameCodec<GameMessage>) {
        this.opcodes[opcode] = codec
    }

    fun find(opcode: Int) : GameCodec<GameMessage>? {
        return get(opcode)
    }

    fun find(clazz: Class<in GameMessage>): GameCodec.CodecRegistration? {
        return messages[clazz]
    }

    override fun toString(): String {
        return "CodecLookupService{messages=${messages}, opcodes=${opcodes}}"
    }
}
