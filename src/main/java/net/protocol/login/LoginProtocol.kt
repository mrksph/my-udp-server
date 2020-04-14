package net.protocol.login

import net.codec.login.LoginStartCodec
import net.handler.login.LoginStartHandler
import net.http.HttpClient
import net.message.login.LoginStartMessage
import net.protocol.BaseProtocol

class LoginProtocol(httpClient: HttpClient)
    : BaseProtocol("LOGIN", 5) {

    init {
        val java = LoginStartMessage::class.java
        val java1 = LoginStartCodec::class.java
        val java2 = LoginStartHandler::class.java
        inbound(0x00,
                java,
                java1,
                java2)
    }
}
