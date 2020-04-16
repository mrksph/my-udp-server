package net.protocol

import io.netty.buffer.ByteBuf
import net.codec.GameCodec
import net.handler.GameMessageHandler
import net.message.GameMessage
import net.service.CodecLookupService
import net.service.HandlerLookupService


abstract class BaseProtocol(var name: String, highestOpCode: Int) : GameProtocol {

    var inboundCodecs: CodecLookupService = CodecLookupService(highestOpCode + 1)
    var outboundCodecs: CodecLookupService = CodecLookupService(highestOpCode + 1)
    var handlers: HandlerLookupService = HandlerLookupService()


    protected open fun inbound(opcode: Int,
                               message: GameMessage,
                               codec: GameCodec<*>,
                               handler: GameMessageHandler<*,*>) {
        try {
            inboundCodecs.bind(message, codec, opcode)
            handlers.bind(message, handler)
        } catch (ex: InstantiationException) {
            System.err.println("Error while instantiating and binding")
        }
    }

    open fun outbound(opcode: Int,
                      message: GameMessage,
                      codec: GameCodec<GameMessage>) {
        try {
            outboundCodecs.bind(message, codec, opcode)
        } catch (ex: InstantiationException) {
            System.err.println("Error while instantiating and binding")
        }
    }

    override fun getCodecRegistration(message: GameMessage): GameCodec.CodecRegistration {
        val find = outboundCodecs.find(message)

        if (find == null) {
//            System.err.println("No codec to write: ${message} in $name")
        }

        return find!!
    }

    override fun readHeader(message: ByteBuf): GameCodec<*> {
        //TODO: SHOULD READ OTHER CODE from message
        val opcode = message.readInt()
        return inboundCodecs.find(opcode)
    }

    fun getMessageHandler(message: GameMessage)
            : GameMessageHandler<*, *>? = handlers.find(message)
}