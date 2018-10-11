package com.cyh.client.handler;

import java.util.UUID;

import com.cyh.protocol.request.LoginRequestPacket;
import com.cyh.protocol.response.LoginResponsePacket;
import com.cyh.utils.LoginUtil;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author: CYH
 * @date: 2018/10/11 19:35
 */
public class ClientLoginHandler extends SimpleChannelInboundHandler<LoginResponsePacket> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("通道已经建立，客户端开始登录");
        LoginRequestPacket requestPacket = new LoginRequestPacket();
        requestPacket.setUserId(UUID.randomUUID().toString());
        requestPacket.setUsername("CYH");
        requestPacket.setPassword("123456");
        ctx.writeAndFlush(requestPacket);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginResponsePacket responsePacket) throws Exception {
        if (responsePacket.getSuccess()) {
            System.out.println("客户端登录成功");
            LoginUtil.markAsLogin(ctx.channel());
        } else {
            System.out.println("客户端登录失败，原因是：" + responsePacket.getReason());
        }
    }
}
