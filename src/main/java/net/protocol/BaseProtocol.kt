package net.protocol

import net.codec.GameCodec
import net.handler.GameMessageHandler
import net.handler.login.LoginStartHandler
import net.message.GameMessage
import net.message.login.LoginStartMessage
import net.service.CodecLookupService
import net.service.HandlerLookupService
import net.session.BaseSession
import kotlin.reflect.KClass


abstract class BaseProtocol(var name: String, highestOpCode: Int) : GameProtocol {

    var inboundCodecs: CodecLookupService = CodecLookupService(highestOpCode + 1)
    var outboundCodecs: CodecLookupService = CodecLookupService(highestOpCode + 1)
    var handlers: HandlerLookupService = HandlerLookupService()

    protected open fun inbound(opcode: Int,
                               message: Class<out GameMessage>,
                               codec: Class<out GameCodec<out GameMessage>>,
                               handler: Class<out GameMessageHandler<out BaseSession, out GameMessage>>) {
        try {
            inboundCodecs.bind(message, codec, opcode)
            handlers.bind(message, handler)
        } catch (ex: InstantiationException) {
            ex.printStackTrace()
            System.err.println("Error while instantianting and binding")
        }

    }

    protected open fun inbound(opcode: Int,
                               message: Class<out GameMessage>,
                               codec: Class<out GameCodec<out GameMessage>>,
                               handler: GameMessageHandler<out BaseSession, out GameMessage>) {
        try {
            inboundCodecs.bind(message, codec, opcode)
            handlers.bind(message, handler)
        } catch (ex: InstantiationException) {
            System.err.println("Error while instantianting and binding")
        }
    }

    open fun outbound(opcode: Int,
                      message: Class<out GameMessage>,
                      codec: Class<out GameCodec<out GameMessage>>) {
        try {
            outboundCodecs.bind(message, codec, opcode)
        } catch (ex: InstantiationException) {
            System.err.println("Error while instantianting and binding")
        }
    }

    override fun getCodecRegistration(clazz: Class<out GameMessage>): GameCodec.CodecRegistration {
        val find = outboundCodecs.find(clazz)

        if (find == null) {
            System.err.println("No codec to write: ${clazz.simpleName} in $name")
        }

        return find!!
    }

    fun getMessageHandler(messageClass: Class<out GameMessage>)
            : GameMessageHandler<out BaseSession, out GameMessage>? = handlers.find(messageClass)

    //TODO: START USING THIS ONE INSTEAD OF JAVA CLASS
    open fun inbound(i: Int, kClass: KClass<LoginStartMessage>, kClass1: KClass<LoginStartHandler>) {}
}