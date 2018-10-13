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
        String fromUserId = responsePacket.getFromUserId();
        String fromUserName = responsePacket.getFromUserName();
        log.debug("{}:{} 说: {}", fromUserId, fromUserName, responsePacket.getMessage());
    }
}
