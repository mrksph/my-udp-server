package util

import io.netty.buffer.ByteBuf
import java.io.IOException
import java.nio.charset.StandardCharsets
import kotlin.experimental.and
import kotlin.experimental.or

object ByteBufUtils {
//
//    @Throws(IOException::class)
//    fun readUTF8(buf: ByteBuf): String? {
//        val len: Int = ByteBufUtils.readVarInt(buf)
//        val bytes = ByteArray(len)
//        buf.readBytes(bytes)
//        return String(bytes, StandardCharsets.UTF_8)
//    }
//    @Throws(IOException::class)
//    fun readVarInt(buf: ByteBuf): Byte {
//        var out : Byte = 0
//        var bytes: Byte = 0
//        var incoming: Byte = 0
//        val maxL : Byte = 127
//        val maxT : Int = 128
//        var incomingInt = 0
//
//        do {
//            incoming = buf.readByte()
//            out = out or (incoming and maxL) shl (bytes++ * 7
//            if (bytes > 5) {
//                throw IOException("Attempt to read int bigger than allowed for a varint!")
//            }
//            incomingInt = incoming.toInt()
//        } while (incomingInt and maxT == maxT)
//        return out
//    }

}