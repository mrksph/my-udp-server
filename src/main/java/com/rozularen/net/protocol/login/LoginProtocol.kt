package com.rozularen.net.protocol.login

import com.rozularen.net.codec.login.LoginStartCodec
import com.rozularen.net.handler.login.LoginStartHandler
import com.rozularen.net.http.HttpClient
import com.rozularen.net.message.login.LoginStartMessage
import com.rozularen.net.protocol.BaseProtocol

class LoginProtocol(httpClient: HttpClient)
    : BaseProtocol("LOGIN", 1) {

    init {
        val message = LoginStartMessage()
        val codec = LoginStartCodec()
        val handler = LoginStartHandler(httpClient)
        inbound(0x00,
                message,
                codec,
                handler)
    }
}
