package com.cyh.client.handler;

import com.cyh.protocol.response.CreateGroupResponsePacket;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: CYH
 * @date: 2018/10/14 0014 17:17
 */
@Slf4j
public class ClientCreateGroupHandler extends SimpleChannelInboundHandler<CreateGroupResponsePacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, CreateGroupResponsePacket responsePacket) throws Exception {
        log.debug("群创建成功！groupId={}，群成员={}", responsePacket.getGroupId(), responsePacket.getUserNameList());
    }
}
