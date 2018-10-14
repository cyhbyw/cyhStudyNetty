package com.cyh.server.handler;

import java.util.List;
import java.util.stream.Collectors;

import com.cyh.protocol.request.ListGroupMembersRequestPacket;
import com.cyh.protocol.response.ListGroupMembersResponsePacket;
import com.cyh.session.Session;
import com.cyh.utils.SessionUtil;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;

/**
 * @author: CYH
 * @date: 2018/10/14 0014 21:32
 */
public class ServerListGroupMembersHandler extends SimpleChannelInboundHandler<ListGroupMembersRequestPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ListGroupMembersRequestPacket requestPacket) {
        String groupId = requestPacket.getGroupId();
        ChannelGroup channelGroup = SessionUtil.getChannelGroup(groupId);
        List<Session> sessionList =
                channelGroup.stream().map(channel -> SessionUtil.getSession(channel)).collect(Collectors.toList());
        ListGroupMembersResponsePacket responsePacket = new ListGroupMembersResponsePacket();
        responsePacket.setGroupId(groupId);
        responsePacket.setSessionList(sessionList);
        ctx.channel().writeAndFlush(responsePacket);
    }
}
