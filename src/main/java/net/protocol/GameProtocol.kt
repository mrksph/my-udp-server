package net.protocol

import net.codec.GameCodec
import net.message.GameMessage

interface GameProtocol {
    fun <M : GameMessage> getCodecRegistration(clazz: Class<M>): GameCodec.CodecRegistration
}