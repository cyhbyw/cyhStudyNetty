package com.cyh.client.handler;

import com.cyh.protocol.response.JoinGroupResponsePacket;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: CYH
 * @date: 2018/10/14 0014 21:02
 */
@Slf4j
public class ClientJoinGroupHandler extends SimpleChannelInboundHandler<JoinGroupResponsePacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, JoinGroupResponsePacket responsePacket) throws Exception {
        if (responsePacket.getSuccess()) {
            log.debug("加入群[{}]成功!", responsePacket.getGroupId());
        }
    }
}
