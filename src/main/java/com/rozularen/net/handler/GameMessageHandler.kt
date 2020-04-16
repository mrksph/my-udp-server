
package com.rozularen.net.handler

import com.rozularen.net.message.GameMessage
import com.rozularen.net.session.BaseSession

abstract class GameMessageHandler<S : BaseSession, T : GameMessage> {

     //TODO: ESTO LO PUEDO QUITAR ASUMIENDO EL TIPO DE S y T EN INTERNAL HANDLE, OJJO

     @Suppress("UNCHECKED_CAST")
     fun handle(session: BaseSession, message: GameMessage){
          val castedSession = session as? S ?:
               throw IllegalArgumentException("Invalid type ${session.javaClass.name} passed to this parser")
          val castedMessage = message as? T?:
               throw IllegalArgumentException("Invalid type ${message.javaClass.name} passed to this parser")
          internalHandle(castedSession, castedMessage)
     }


     abstract fun internalHandle(session: S, message: T)

}