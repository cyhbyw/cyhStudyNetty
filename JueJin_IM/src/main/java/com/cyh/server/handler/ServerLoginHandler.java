package com.cyh.server.handler;

import java.util.UUID;

import com.cyh.protocol.request.LoginRequestPacket;
import com.cyh.protocol.response.LoginResponsePacket;
import com.cyh.session.Session;
import com.cyh.utils.SessionUtil;

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
        String username = loginRequestPacket.getUsername();
        responsePacket.setUsername(username);

        if (valid(loginRequestPacket)) {
            responsePacket.setSuccess(true);
            String userId = UUID.randomUUID().toString().split("-")[0];
            responsePacket.setUserId(userId);
            log.debug("[" + username + "]登录成功");

            Session session = new Session(userId, username);
            SessionUtil.bindSession(session, ctx.channel());
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

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        SessionUtil.unbindSession(ctx.channel());
    }
}
