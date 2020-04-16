package com.rozularen.net.protocol.login

import com.rozularen.net.codec.login.LoginStartCodec
import com.rozularen.net.handler.login.LoginStartHandler
import com.rozularen.net.http.HttpClient
import com.rozularen.net.message.login.LoginStartMessage
import com.rozularen.net.protocol.BaseProtocol

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
        val message = LoginStartMessage(1)
        val codec = LoginStartCodec("LoginStartCodec")
        val handler = LoginStartHandler(httpClient)
        inbound(0x00,
                message,
                codec,
                handler)
    }
}
