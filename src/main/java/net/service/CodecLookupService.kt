package net.service

import net.message.GameMessage
import net.codec.GameCodec
import java.lang.reflect.InvocationTargetException
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap
import java.util.concurrent.atomic.AtomicInteger

class CodecLookupService(size: Int) {

    private lateinit var messages: ConcurrentMap<Class<out GameMessage>, GameCodec.CodecRegistration>
    private var opcodes: ConcurrentMap<Int, GameCodec<GameMessage>>?
    private var opcodeTable: Array<GameCodec<GameMessage>?>
    private var nextId: AtomicInteger

    init {
        if (size < 0) {
            throw IllegalArgumentException("Size cannot be < 0!")
        } else {
            if (size == 0) {
                this.opcodes = ConcurrentHashMap<Int, GameCodec<GameMessage>>()
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
    fun <M : GameMessage, C : GameCodec<in M>> bind(messageClass: Class<M>, codec: Class<C>, opcode: Int) {
        val reg = messages[messageClass]
        //TODO: bind
    }

    fun <M : GameMessage?> find(clazz: Class<M>): GameCodec.CodecRegistration? {
        return messages[clazz]
    }

}
