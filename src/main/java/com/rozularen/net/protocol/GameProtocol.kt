package com.rozularen.net.protocol

import com.rozularen.net.codec.GameCodec
import com.rozularen.net.message.GameMessage
import io.netty.buffer.ByteBuf

interface GameProtocol {
    fun  getCodecRegistration(message: GameMessage): GameCodec.CodecRegistration
    fun readHeader(message: ByteBuf): GameCodec<*>

}