package com.rozularen.net.codec.login

import com.rozularen.net.codec.GameCodec
import com.rozularen.net.message.login.LoginStartMessage
import io.netty.buffer.ByteBuf

class LoginStartCodec(private val name: String) : GameCodec<LoginStartMessage>() {
    override fun encode(var1: ByteBuf, var2: LoginStartMessage): ByteBuf {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun decode(var1: ByteBuf): LoginStartMessage {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getCodecName(): String {
        return name
    }
}