package net.protocol.login

import net.codec.GameCodec
import net.codec.login.LoginStartCodec
import net.handler.login.LoginStartHandler
import net.http.HttpClient
import net.message.GameMessage
import net.message.login.LoginStartMessage
import net.protocol.BaseProtocol

class LoginProtocol(httpClient: HttpClient)
    : BaseProtocol("LOGIN", 5) {

    init {
//        val java = LoginStartMessage::class
//        val java1 = LoginStartCodec::class
//        val java2 = LoginStartHandler::class
//        inbound(0x00,
//                java,
//                java1,
//                java2)
        val message = LoginStartMessage()
        val codec = LoginStartCodec("LoginStartCodec")
        val handler = LoginStartHandler(httpClient)
        inbound(0x00,
                message,
                codec,
                handler)
    }
}
