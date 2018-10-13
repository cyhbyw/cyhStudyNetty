package com.cyh.server.handler;

import com.cyh.utils.SessionUtil;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: CYH
 * @date: 2018/10/13 0013 17:22
 */
@Slf4j
public class AuthHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (!SessionUtil.hasLogin(ctx.channel())) {
            log.warn("未登陆，强制关闭通道");
            ctx.channel().close();
        } else {
            ctx.pipeline().remove(this);
            super.channelRead(ctx, msg);
        }
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        if (SessionUtil.hasLogin(ctx.channel())) {
            log.debug("当前连接登录验证完毕，无需再次验证, AuthHandler 被移除");
        } else {
            log.warn("未登陆，强制关闭通道");
        }
    }
}
