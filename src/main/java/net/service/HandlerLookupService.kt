@file:Suppress("UNCHECKED_CAST")

package net.service

import net.handler.GameMessageHandler
import net.message.GameMessage
import net.session.BaseSession
import java.util.*

//class HandlerLookupService {
//    //    private val handlers: MutableMap<Class<in GameMessage>, GameMessageHandler<in BaseSession, in GameMessage>> = HashMap()
//    private val handlers: HashMap<Any?, Any?> = HashMap()
//
//
//    fun <M : GameMessage, H : GameMessageHandler<in BaseSession, in GameMessage>>
//            bind(clazz: Class<M>, handlerClass: Class<H>) {
//        bind<M, H>(clazz, (handlerClass.newInstance() as GameMessageHandler<*,*>))
//    }
////
////    fun <M: GameMessage, H : GameMessageHandler<in BaseSession, in GameMessage>> bind(clazz: Class<in GameMessage>, handler: H) {
////        this.handlers[clazz] = handler
////    }
//
//
//    @Throws(InstantiationException::class, IllegalAccessException::class)
//    fun <M : GameMessage?, H : GameMessageHandler<*, in M>?> bind(clazz: Class<M>?, handler: H) {
//        handlers[clazz] = handler
//    }
//
//    fun find(messageClass: Class<in GameMessage>): GameMessageHandler<in BaseSession, in GameMessage> {
//        return handlers[messageClass] as GameMessageHandler<in BaseSession, in GameMessage>
//    }(GameMessageHandler)handlerClass.newInstance()
//this.bind(clazz, (MessageHandler)handlerClass.newInstance());
//
//}


class HandlerLookupService {
    private val handlers: MutableMap<Class<out GameMessage>, GameMessageHandler<out BaseSession, out GameMessage>> = HashMap()

    @Throws(InstantiationException::class, IllegalAccessException::class)
    fun bind(clazz: Class<out GameMessage>, handlerClass: Class<out GameMessageHandler<out BaseSession, out GameMessage>>) {
        val handlerInstance = handlerClass.newInstance()
        this.bind(clazz, handlerInstance)
    }

    @Throws(InstantiationException::class, IllegalAccessException::class)
    fun bind(clazz: Class<out GameMessage>, handler: GameMessageHandler<out BaseSession, out GameMessage>) {
        handlers[clazz] = handler
    }

    fun find(clazz: Class<out GameMessage>): GameMessageHandler<out BaseSession, out GameMessage>? {
        return handlers[clazz]
    }

    override fun toString(): String {
        return "HandlerLookupService{handlers=$handlers}"
    }
}
