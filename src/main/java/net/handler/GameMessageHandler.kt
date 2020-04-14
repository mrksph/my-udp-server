
package net.handler

import net.message.GameMessage
import net.session.BaseSession

abstract class GameMessageHandler<S : BaseSession, T : GameMessage> {

     @Suppress("UNCHECKED_CAST")
     fun handle(session: BaseSession, message: GameMessage){
          val castedSession = session as? S ?: throw IllegalArgumentException("Invalid type ${session.javaClass.name} passed to this parser")
          val castedMessage = message as? T?: throw IllegalArgumentException("Invalid type ${message.javaClass.name} passed to this parser")
          internalHandle(castedSession, castedMessage)
     }


     abstract fun internalHandle(session: S, message: T)

}