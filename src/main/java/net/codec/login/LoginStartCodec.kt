package net.codec.login

import io.netty.buffer.ByteBuf
import net.codec.GameCodec
import net.message.login.LoginStartMessage

class LoginStartCodec: GameCodec<LoginStartMessage>{
    override fun encode(var1: ByteBuf): LoginStartMessage {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun decode(var1: ByteBuf, var2: LoginStartMessage): ByteBuf {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}