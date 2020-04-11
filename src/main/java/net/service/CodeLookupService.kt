package net.service

import net.message.Message
import net.codec.Codec
import java.lang.reflect.InvocationTargetException
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap
import java.util.concurrent.atomic.AtomicInteger

class CodeLookupService(size: Int) {

    private lateinit var messages: ConcurrentMap<Class<out Message>, Codec.CodecRegistration>
    private var opcodes: ConcurrentMap<Int, Codec<Message>>?
    private var opcodeTable: Array<Codec<Message>?>
    private var nextId: AtomicInteger

    init {
        if (size < 0) {
            throw IllegalArgumentException("Size cannot be < 0!")
        } else {
            if (size == 0) {
                this.opcodes = ConcurrentHashMap<Int, Codec<Message>>()
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
    fun <M : Message, C : Codec<in M>> bind(messageClass: Class<M>, codec: Class<C>, opcode: Int) {
        val reg = messages[messageClass]
    }

    fun <M : Message?> find(clazz: Class<M>): Codec.CodecRegistration? {
        return messages[clazz]
    }

}
