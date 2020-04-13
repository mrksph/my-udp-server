package net.protocol

import net.codec.GameCodec
import net.handler.GameMessageHandler
import net.message.GameMessage
import net.service.CodecLookupService
import net.service.HandlerLookupService
import net.session.BaseSession


abstract class BaseProtocol(var name: String, highestOpCode: Int) : GameProtocol {

    var inboundCodecs: CodecLookupService = CodecLookupService(highestOpCode + 1)
    var outboundCodecs: CodecLookupService = CodecLookupService(highestOpCode + 1)
    var handlers: HandlerLookupService = HandlerLookupService()

    protected open fun <M : GameMessage, C : GameCodec<in M>, H : GameMessageHandler<*, in M>>
            inbound(opcode: Int, message: Class<M>, codec: Class<C>, handler: Class<H>?) {
        try {
            inboundCodecs.bind(message, codec, opcode)
            handlers.bind(message, handler!!)
        } catch (ex: InstantiationException) {
            System.err.println("Error while instantianting and binding")
        }
    }

    fun <M : GameMessage> getMessageHandler(javaClass: Class<GameMessage>)
            : GameMessageHandler<in BaseSession, GameMessage>? {
        return handlers.find(javaClass)
    }


    protected open fun <M : GameMessage, C : GameCodec<in M>, H : GameMessageHandler<*, in M>>
            inbound(opcode: Int, message: Class<M>, codec: Class<C>, handler: H?) {
        try {
            inboundCodecs.bind(message, codec, opcode)
            handlers.bind(message, handler!!)
        } catch (ex: InstantiationException) {
            System.err.println("Error while instantianting and binding")
        }
    }

    open fun <M : GameMessage, C : GameCodec<in M>>
            outbound(opcode: Int, message: Class<M>, codec: Class<C>) {
        try {
            outboundCodecs.bind(message, codec, opcode)
        } catch (ex: InstantiationException) {
            System.err.println("Error while instantianting and binding")
        }
    }

    override fun <M : GameMessage> getCodecRegistration(clazz: Class<M>): GameCodec.CodecRegistration {
        val find = outboundCodecs.find(clazz)

        if (find == null) {
            System.err.println("No codec to write: ${clazz.simpleName} in $name")
        }

        return find!!
    }
}