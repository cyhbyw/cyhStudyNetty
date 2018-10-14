package com.cyh.client.handler;

import com.cyh.protocol.response.GroupMessageResponsePacket;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: CYH
 * @date: 2018/10/14 0014 22:15
 */
@Slf4j
public class ClientGroupMessageHandler extends SimpleChannelInboundHandler<GroupMessageResponsePacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupMessageResponsePacket responsePacket) {
        log.debug("群 {} 的消息：用户 {} 说【{}】", responsePacket.getGroupId(), responsePacket.getSendUser(),
                responsePacket.getMessage());
    }
}
