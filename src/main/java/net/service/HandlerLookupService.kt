package net.service

import net.message.GameMessage
import net.message.GameMessageHandler
import java.util.*

class HandlerLookupService {
    private val handlers: Map<Class<out GameMessage?>?, GameMessageHandler<*, *>?> = HashMap()

    fun <M : GameMessage?, H : GameMessageHandler<*, in M>?>
            bind(clazz: Class<M>?, handler: H) {

        //
    }


    fun <M : GameMessage?, H : GameMessageHandler<*, in M>?>
            bind(clazz: Class<M>?, handlerClass: Class<H>) {
        bind(clazz, handlerClass.newInstance())
    }

}
