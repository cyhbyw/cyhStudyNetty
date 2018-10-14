package com.cyh.client.handler;

import com.cyh.protocol.response.ListGroupMembersResponsePacket;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: CYH
 * @date: 2018/10/14 0014 21:36
 */
@Slf4j
public class ClientListGroupMembersHandler extends SimpleChannelInboundHandler<ListGroupMembersResponsePacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ListGroupMembersResponsePacket responsePacket) {
        log.debug("群聊 {} 中的成员是 {}", responsePacket.getGroupId(), responsePacket.getSessionList());
    }
}
