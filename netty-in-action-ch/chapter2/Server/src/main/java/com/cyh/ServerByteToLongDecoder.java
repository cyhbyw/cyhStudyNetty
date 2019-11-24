package com.cyh;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ServerByteToLongDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        log.debug("com.cyh.MyByteToLongDecoder.decode");
        TimeUnit.SECONDS.sleep(2);
        while (in.readableBytes() >= 8) {
            out.add(in.readLong());
        }
    }
}
