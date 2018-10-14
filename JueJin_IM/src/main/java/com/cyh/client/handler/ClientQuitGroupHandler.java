package com.cyh.client.handler;

import com.cyh.protocol.response.QuitGroupResponsePacket;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: CYH
 * @date: 2018/10/14 0014 21:17
 */
@Slf4j
public class ClientQuitGroupHandler extends SimpleChannelInboundHandler<QuitGroupResponsePacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, QuitGroupResponsePacket responsePacket) throws Exception {
        if (responsePacket.getSuccess()) {
            log.debug("退出群聊 [{}] 成功!", responsePacket.getGroupId());
        }
    }
}
