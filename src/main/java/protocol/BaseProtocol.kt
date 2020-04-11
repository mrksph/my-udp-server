package protocol

import net.protocol.Protocol

abstract class BaseProtocol : Protocol {
    lateinit var name: String
}