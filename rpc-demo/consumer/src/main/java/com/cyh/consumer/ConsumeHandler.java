package com.cyh.consumer;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author: CYH
 * @date: 2018/10/8 11:11
 */
public class ConsumeHandler extends SimpleChannelInboundHandler {

    private Object response;

    public Object getResponse() {
        return response;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) {
        System.out.println("客户端收到服务端返回的消息: " + msg);
        this.response = msg;
    }
}
