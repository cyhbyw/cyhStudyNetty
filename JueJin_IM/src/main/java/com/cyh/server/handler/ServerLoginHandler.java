package com.cyh.server.handler;

import com.cyh.protocol.request.LoginRequestPacket;
import com.cyh.protocol.response.LoginResponsePacket;
import com.cyh.utils.LoginUtil;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: CYH
 * @date: 2018/10/11 19:46
 */
@Slf4j
public class ServerLoginHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginRequestPacket loginRequestPacket) {
        log.debug("客户端开始登录");
        LoginResponsePacket responsePacket = new LoginResponsePacket();
        responsePacket.setVersion(loginRequestPacket.getVersion());
        if (valid(loginRequestPacket)) {
            LoginUtil.markAsLogin(ctx.channel());
            responsePacket.setSuccess(true);
            log.debug("登录成功");
        } else {
            responsePacket.setSuccess(false);
            responsePacket.setReason("账号密码不对");
            log.debug("登录失败");
        }
        ctx.writeAndFlush(responsePacket);
    }

    private boolean valid(LoginRequestPacket packet) {
        return true;
    }
}
