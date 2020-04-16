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
        return LoginStartMessage(0, username)
    }

    override fun encode(buffer: ByteBuf, message: LoginStartMessage): ByteBuf {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getCodecName(): String {
        return name
    }
}