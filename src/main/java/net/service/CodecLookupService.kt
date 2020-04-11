package net.service

import net.message.GameMessage
import net.codec.Codec
import java.lang.reflect.InvocationTargetException
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap
import java.util.concurrent.atomic.AtomicInteger

class CodecLookupService(size: Int) {

    private lateinit var messages: ConcurrentMap<Class<out GameMessage>, Codec.CodecRegistration>
    private var opcodes: ConcurrentMap<Int, Codec<GameMessage>>?
    private var opcodeTable: Array<Codec<GameMessage>?>
    private var nextId: AtomicInteger

    init {
        if (size < 0) {
            throw IllegalArgumentException("Size cannot be < 0!")
        } else {
            if (size == 0) {
                this.opcodes = ConcurrentHashMap<Int, Codec<GameMessage>>()
                this.opcodeTable = emptyArray()
            } else {
                this.opcodeTable = arrayOfNulls(size)
                this.opcodes = null
            }
            this.nextId = AtomicInteger(0)
        }
    }

    @Throws(InstantiationException::class,
            IllegalAccessException::class,
            InvocationTargetException::class)
    fun <M : GameMessage, C : Codec<in M>> bind(messageClass: Class<M>, codec: Class<C>, opcode: Int) {
        val reg = messages[messageClass]
        //TODO: bind
    }

    fun <M : GameMessage?> find(clazz: Class<M>): Codec.CodecRegistration? {
        return messages[clazz]
    }

}
