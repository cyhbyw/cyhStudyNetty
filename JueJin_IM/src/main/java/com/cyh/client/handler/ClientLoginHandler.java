package com.cyh.client.handler;

import java.util.UUID;

import com.cyh.protocol.request.LoginRequestPacket;
import com.cyh.protocol.response.LoginResponsePacket;
import com.cyh.utils.LoginUtil;

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
    public void channelActive(ChannelHandlerContext ctx) {
        log.debug("通道已经建立，客户端开始登录");
        LoginRequestPacket requestPacket = new LoginRequestPacket();
        requestPacket.setUserId(UUID.randomUUID().toString());
        requestPacket.setUsername("CYH");
        requestPacket.setPassword("123456");
        ctx.writeAndFlush(requestPacket);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginResponsePacket responsePacket) {
        if (responsePacket.getSuccess()) {
            log.debug("客户端登录成功");
            LoginUtil.markAsLogin(ctx.channel());
        } else {
            log.debug("客户端登录失败，原因是：" + responsePacket.getReason());
        }
    }
}
