package com.rozularen.net.protocol

import com.rozularen.net.codec.GameCodec
import com.rozularen.net.handler.GameMessageHandler
import com.rozularen.net.message.GameMessage
import com.rozularen.net.service.CodecLookupService
import com.rozularen.net.service.HandlerLookupService
import io.netty.buffer.ByteBuf


abstract class BaseProtocol(var name: String, highestOpCode: Int) : GameProtocol {

    var inboundCodecs: CodecLookupService = CodecLookupService(highestOpCode + 1)
    var outboundCodecs: CodecLookupService = CodecLookupService(highestOpCode + 1)
    var handlers: HandlerLookupService = HandlerLookupService()


    protected open fun inbound(opcode: Int,
                               message: GameMessage,
                               codec: GameCodec<*>,
                               handler: GameMessageHandler<*, *>) {
        try {
            inboundCodecs.bind(message.getName(), codec, opcode)
            handlers.bind(message.getName(), handler)
        } catch (ex: InstantiationException) {
            System.err.println("Error while instantiating and binding")
        }
    }

    open fun outbound(opcode: Int,
                      message: GameMessage,
                      codec: GameCodec<GameMessage>) {
        try {
            outboundCodecs.bind(message.getName(), codec, opcode)
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