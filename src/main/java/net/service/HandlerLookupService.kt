package net.service

import net.message.Message
import net.message.MessageHandler
import java.util.*

class HandlerLookupService {
    private val handlers: Map<Class<out Message?>?, MessageHandler<*, *>?> = HashMap()

    fun <M : Message?, H : MessageHandler<*, in M>?>
            bind(clazz: Class<M>?, handler: H) {

        //
    }


    fun <M : Message?, H : MessageHandler<*, in M>?>
            bind(clazz: Class<M>?, handlerClass: Class<H>) {
        bind(clazz, handlerClass.newInstance())
    }

}
