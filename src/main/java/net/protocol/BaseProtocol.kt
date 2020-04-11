package net.protocol

import net.codec.Codec
import net.message.Message
import net.message.MessageHandler
import net.service.CodeLookupService
import net.service.HandlerLookupService


abstract class BaseProtocol(var name: String, highestOpCode: Int) : Protocol {

    var inboundCodecs: CodeLookupService = CodeLookupService(highestOpCode + 1)
    var outboundCodecs: CodeLookupService = CodeLookupService(highestOpCode + 1)
    var handlers: HandlerLookupService = HandlerLookupService()

    protected open fun <M : Message, C : Codec<in M>, H : MessageHandler<*, in M>>
            inbound(opcode: Int, message: Class<M>, codec: Class<C>, handler: Class<H>?) {
        try {
            inboundCodecs.bind(message, codec, opcode)
            handlers.bind(message, handler!!)
        } catch (ex: InstantiationException) {
            System.err.println("Error while instantianting and binding")
        }
    }

    protected open fun <M : Message, C : Codec<in M>, H : MessageHandler<*, in M>>
            inbound(opcode: Int, message: Class<M>, codec: Class<C>, handler: H?) {
        try {
            inboundCodecs.bind(message, codec, opcode)
            handlers.bind(message, handler!!)
        } catch (ex: InstantiationException) {
            System.err.println("Error while instantianting and binding")
        }
    }

    open fun <M : Message, C : Codec<in M>>
            outbound(opcode: Int, message: Class<M>, codec: Class<C>) {
        try {
            outboundCodecs.bind(message, codec, opcode)
        } catch (ex: InstantiationException) {
            System.err.println("Error while instantianting and binding")
        }
    }

    override fun <M : Message> getCodecRegistration(clazz: Class<M>): Codec.CodecRegistration {
        val find = outboundCodecs.find(clazz)

        if (find == null) {
            System.err.println("No codec to write: ${clazz.simpleName} in $name")
        }

        return find!!
    }
}