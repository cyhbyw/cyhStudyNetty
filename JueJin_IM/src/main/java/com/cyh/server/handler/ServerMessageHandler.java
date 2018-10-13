package com.cyh.server.handler;

import java.util.Objects;

import com.cyh.protocol.request.MessageRequestPacket;
import com.cyh.protocol.response.MessageResponsePacket;
import com.cyh.session.Session;
import com.cyh.utils.SessionUtil;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: CYH
 * @date: 2018/10/11 19:46
 */
@Slf4j
public class ServerMessageHandler extends SimpleChannelInboundHandler<MessageRequestPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageRequestPacket messageRequestPacket) {
        log.debug("服务器收到客户端发来的消息：" + messageRequestPacket.getMessage());
        Session session = SessionUtil.getSession(ctx.channel());
        MessageResponsePacket responsePacket = new MessageResponsePacket();
        responsePacket.setFromUserId(session.getUserId());
        responsePacket.setFromUserName(session.getUsername());
        responsePacket.setMessage(messageRequestPacket.getMessage());

        String toUserId = messageRequestPacket.getToUserId();
        Channel toUserChannel = SessionUtil.getChannel(toUserId);
        if (Objects.nonNull(toUserChannel) && SessionUtil.hasLogin(toUserChannel)) {
            toUserChannel.writeAndFlush(responsePacket);
        } else {
            log.warn("[" + toUserId + "] 不在线，发送失败!");
        }
    }
}
