package com.cyh.codec;

import com.cyh.protocol.command.PacketCodeC;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: CYH
 * @date: 2018/10/13 0013 16:30
 */
@Slf4j
public class Splitter extends LengthFieldBasedFrameDecoder {

    private static final Integer LENGTH_FIELD_OFFSET = 7;
    private static final Integer LENGTH_FIELD_LENGTH = 4;

    public Splitter() {
        super(Integer.MAX_VALUE, LENGTH_FIELD_OFFSET, LENGTH_FIELD_LENGTH);
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf byteBuf) throws Exception {
        if (byteBuf.getInt(byteBuf.readerIndex()) != PacketCodeC.MAGIC_NUMBER) {
            log.warn("不是指定消息（魔法数非法），关闭通道，结束");
            ctx.channel().close();
            return null;
        }
        return super.decode(ctx, byteBuf);
    }
}
