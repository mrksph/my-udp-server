package net.protocol

import net.codec.GameCodec
import net.handler.GameMessageHandler
import net.message.GameMessage

interface GameProtocol {
    fun  getCodecRegistration(clazz: Class<in GameMessage>): GameCodec.CodecRegistration

}