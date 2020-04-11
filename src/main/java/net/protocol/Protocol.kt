package net.protocol

import net.codec.Codec
import net.message.GameMessage

interface Protocol {
    fun <M : GameMessage> getCodecRegistration(clazz: Class<M>): Codec.CodecRegistration
}