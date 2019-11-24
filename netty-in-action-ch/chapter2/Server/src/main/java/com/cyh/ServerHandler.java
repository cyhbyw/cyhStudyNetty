package com.cyh;

import java.util.concurrent.TimeUnit;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ServerHandler extends SimpleChannelInboundHandler<Long> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Long msg) throws Exception {
        log.debug("Server received msg: " + msg);
        TimeUnit.SECONDS.sleep(2);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        log.debug("com.cyh.MyServerHandler.channelReadComplete");
        TimeUnit.SECONDS.sleep(2);
        ctx.writeAndFlush(456L);
    }
}
