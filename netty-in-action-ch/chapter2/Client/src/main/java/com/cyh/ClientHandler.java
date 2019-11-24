package com.cyh;

import java.util.concurrent.TimeUnit;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ClientHandler extends SimpleChannelInboundHandler<Long> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.debug("com.cyh.MyClientHandler.channelActive");
        TimeUnit.SECONDS.sleep(2);
        ctx.writeAndFlush(123L);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Long msg) throws Exception {
        log.debug("Client received msg: " + msg);
        TimeUnit.SECONDS.sleep(2);
    }
}
