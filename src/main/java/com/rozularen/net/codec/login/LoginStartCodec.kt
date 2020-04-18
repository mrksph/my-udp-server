package com.rozularen.net.codec.login

import com.rozularen.net.codec.GameCodec
import com.rozularen.net.message.login.LoginStartMessage
import io.netty.buffer.ByteBuf
import io.netty.util.CharsetUtil

class LoginStartCodec()
    : GameCodec<LoginStartMessage>() {
    private val name = "LoginStartCodec"

    override fun decode(buffer: ByteBuf): LoginStartMessage {
        val username = buffer.readBytes(5).toString(CharsetUtil.UTF_8)
        return LoginStartMessage(username)
    }

    override fun encode(buffer: ByteBuf, message: LoginStartMessage): ByteBuf {
        buffer.writeBytes(message.username.toByteArray(CharsetUtil.UTF_8))
        return buffer
    }

    override fun getCodecName(): String = name

}