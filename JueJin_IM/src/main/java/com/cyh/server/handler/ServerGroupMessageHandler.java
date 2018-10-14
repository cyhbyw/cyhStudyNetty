package com.cyh.server.handler;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.cyh.protocol.request.GroupMessageRequestPacket;
import com.cyh.protocol.response.GroupMessageResponsePacket;
import com.cyh.utils.SessionUtil;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;

/**
 * @author: CYH
 * @date: 2018/10/14 0014 22:02
 */
public class ServerGroupMessageHandler extends SimpleChannelInboundHandler<GroupMessageRequestPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupMessageRequestPacket requestPacket) throws Exception {
        String groupId = requestPacket.getGroupId();
        String message = requestPacket.getMessage();

        ChannelGroup channelGroup = SessionUtil.getChannelGroup(groupId);
        List<Channel> toSendChannelList = channelGroup.stream()
                .filter(channel -> !Objects.equals(channel, ctx.channel())).collect(Collectors.toList());
        ChannelGroup toSendChannelGroup = new DefaultChannelGroup(ctx.executor());
        toSendChannelGroup.addAll(toSendChannelList);

        GroupMessageResponsePacket responsePacket = new GroupMessageResponsePacket();
        responsePacket.setSendUser(SessionUtil.getSession(ctx.channel()));
        responsePacket.setGroupId(groupId);
        responsePacket.setMessage(message);
        toSendChannelGroup.writeAndFlush(responsePacket);
    }
}
