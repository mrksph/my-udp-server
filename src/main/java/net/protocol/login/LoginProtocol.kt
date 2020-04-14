package net.protocol.login

import net.codec.login.LoginStartCodec
import net.handler.login.LoginStartHandler
import net.http.HttpClient
import net.message.login.LoginStartMessage
import net.protocol.BaseProtocol

class LoginProtocol(httpClient: HttpClient)
    : BaseProtocol("LOGIN", 5) {

    init {
        inbound(0x00,
                LoginStartMessage::class.java,
                LoginStartCodec::class.java,
                LoginStartHandler::class.java)
    }
}
