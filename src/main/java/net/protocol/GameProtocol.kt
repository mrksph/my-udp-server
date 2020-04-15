package net.protocol

import io.netty.buffer.ByteBuf
import net.codec.GameCodec
import net.handler.GameMessageHandler
import net.message.GameMessage
import kotlin.reflect.KClass

interface GameProtocol {
    fun  getCodecRegistration(message: GameMessage): GameCodec.CodecRegistration
    fun readHeader(message: ByteBuf): GameCodec<*>

}