package com.cyh.codec;

import com.cyh.protocol.command.Packet;
import com.cyh.protocol.command.PacketCodeC;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: CYH
 * @date: 2018/10/11 19:33
 */
@Slf4j
public class PacketEncoder extends MessageToByteEncoder<Packet> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Packet packet, ByteBuf byteBuf) {
        log.debug("即将被编码的Packet: " + packet);
        PacketCodeC.INSTANCE.encode(byteBuf, packet);
    }
}
