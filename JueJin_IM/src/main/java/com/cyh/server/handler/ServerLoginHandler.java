package com.cyh.server.handler;

import com.cyh.protocol.request.LoginRequestPacket;
import com.cyh.protocol.response.LoginResponsePacket;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author: CYH
 * @date: 2018/10/11 19:46
 */
public class ServerLoginHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginRequestPacket loginRequestPacket) throws Exception {
        System.out.println("客户端开始登录");
        LoginResponsePacket responsePacket = new LoginResponsePacket();
        responsePacket.setVersion(loginRequestPacket.getVersion());
        if (valid(loginRequestPacket)) {
            responsePacket.setSuccess(true);
            System.out.println("登录成功");
        } else {
            responsePacket.setSuccess(false);
            responsePacket.setReason("账号密码不对");
            System.out.println("登录失败");
        }
        ctx.writeAndFlush(responsePacket);
    }

    private boolean valid(LoginRequestPacket packet) {
        return true;
    }
}
