package game.session

import game.GameServer
import game.MainServer
import io.netty.channel.Channel
import protocol.ProtocolProvider

class Session(server: MainServer, protocolProvider: ProtocolProvider, channel: Channel, gameServer: GameServer)