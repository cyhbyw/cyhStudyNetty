package com.cyh.client.handler;

import com.cyh.protocol.response.LoginResponsePacket;
import com.cyh.session.Session;
import com.cyh.utils.SessionUtil;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: CYH
 * @date: 2018/10/11 19:35
 */
@Slf4j
public class ClientLoginHandler extends SimpleChannelInboundHandler<LoginResponsePacket> {


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginResponsePacket responsePacket) {
        String userId = responsePacket.getUserId();
        String username = responsePacket.getUsername();

        if (responsePacket.getSuccess()) {
            log.debug("客户端登录成功: userId={}, username={}", userId, username);
            Session session = new Session(userId, username);
            SessionUtil.bindSession(session, ctx.channel());
        } else {
            log.debug("客户端登录失败，原因是：" + responsePacket.getReason());
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.warn("客户端连接被关闭");
    }
}
