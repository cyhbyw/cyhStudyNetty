package com.cyh.server.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.cyh.protocol.request.CreateGroupRequestPacket;
import com.cyh.protocol.response.CreateGroupResponsePacket;
import com.cyh.session.Session;
import com.cyh.utils.IDUtil;
import com.cyh.utils.SessionUtil;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: CYH
 * @date: 2018/10/14 0014 17:09
 */
@Slf4j
public class ServerCreateGroupHandler extends SimpleChannelInboundHandler<CreateGroupRequestPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, CreateGroupRequestPacket requestPacket) throws Exception {
        List<String> userIdList = requestPacket.getUserIdList();
        ChannelGroup channelGroup = new DefaultChannelGroup(ctx.executor());
        List<String> usernameList = new ArrayList<>();

        userIdList.stream().forEach(userId -> {
            Channel channel = SessionUtil.getChannel(userId);
            if (Objects.nonNull(channel)) {
                channelGroup.add(channel);
                Session session = SessionUtil.getSession(channel);
                usernameList.add(session.getUsername());
            }
        });

        CreateGroupResponsePacket responsePacket = new CreateGroupResponsePacket();
        responsePacket.setSuccess(true);
        String groupId = IDUtil.randomId();
        responsePacket.setGroupId(groupId);
        responsePacket.setUserNameList(usernameList);
        channelGroup.writeAndFlush(responsePacket);

        SessionUtil.bindChannelGroup(groupId, channelGroup);

        log.debug("群创建成功！groupId={}，群成员={}", groupId, usernameList);
    }
}
