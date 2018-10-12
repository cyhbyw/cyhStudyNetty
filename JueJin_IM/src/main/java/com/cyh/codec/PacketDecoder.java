package com.cyh.codec;

import java.util.List;

import com.cyh.protocol.command.Packet;
import com.cyh.protocol.command.PacketCodeC;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: CYH
 * @date: 2018/10/11 19:34
 */
@Slf4j
public class PacketDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf byteBuf, List<Object> out) {
        Packet packet = PacketCodeC.INSTANCE.decode(byteBuf);
        out.add(packet);
        log.debug("解码得到的Packet: " + packet);
    }
}
