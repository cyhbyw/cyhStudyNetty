package com.cyh.client.handler;

import com.cyh.protocol.response.MessageResponsePacket;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: CYH
 * @date: 2018/10/11 19:38
 */
@Slf4j
public class ClientMessageHandler extends SimpleChannelInboundHandler<MessageResponsePacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageResponsePacket responsePacket) {
        log.debug("客户端收到服务端回复的消息：" + responsePacket.getMessage());
    }
}
