package com.cyh.client.handler;

import com.cyh.protocol.response.MessageResponsePacket;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author: CYH
 * @date: 2018/10/11 19:38
 */
public class ClientMessageHandler extends SimpleChannelInboundHandler<MessageResponsePacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageResponsePacket responsePacket) throws Exception {
        System.out.println("客户端收到服务端回复的消息：" + responsePacket.getMessage());
    }
}
