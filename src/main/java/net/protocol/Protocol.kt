package net.protocol

import net.codec.Codec
import net.message.Message

interface Protocol {
    fun <M : Message> getCodecRegistration(clazz: Class<M>): Codec.CodecRegistration
}