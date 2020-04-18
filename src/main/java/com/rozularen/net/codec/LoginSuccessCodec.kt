package com.rozularen.net.codec

import com.rozularen.net.message.login.LoginSuccessMessage
import io.netty.buffer.ByteBuf
import io.netty.util.CharsetUtil

class LoginSuccessCodec
    : GameCodec<LoginSuccessMessage>() {

    private val name = "LoginSuccessCodec"

    override fun decode(buffer: ByteBuf): LoginSuccessMessage {
        val uuid = ""
        val username = ""
        return LoginSuccessMessage(uuid, username)
    }

    override fun encode(buffer: ByteBuf, message: LoginSuccessMessage): ByteBuf {
        buffer.writeBytes(message.uuid.toByteArray(CharsetUtil.UTF_8))
        buffer.writeBytes(message.username.toByteArray(CharsetUtil.UTF_8))
        return buffer
    }

    override fun getCodecName() = this.name

}
