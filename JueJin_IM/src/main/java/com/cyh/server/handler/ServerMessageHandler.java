package com.cyh.server.handler;

import com.cyh.protocol.request.MessageRequestPacket;
import com.cyh.protocol.response.MessageResponsePacket;

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
        log.debug("客户端说：" + messageRequestPacket.getMessage());
        MessageResponsePacket responsePacket = new MessageResponsePacket();
        responsePacket.setMessage("服务端回复【" + messageRequestPacket.getMessage() + "】");
        ctx.writeAndFlush(responsePacket);
    }
}
