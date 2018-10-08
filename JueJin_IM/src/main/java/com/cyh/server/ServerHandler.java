package com.cyh.server;

import com.cyh.protocol.command.Packet;
import com.cyh.protocol.command.PacketCodeC;
import com.cyh.protocol.request.LoginRequestPacket;
import com.cyh.protocol.response.LoginResponsePacket;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author: CYH
 * @date: 2018/10/8 18:24
 */
public class ServerHandler extends SimpleChannelInboundHandler {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("客户端开始登录");
        ByteBuf requestBuf = (ByteBuf) msg;
        Packet packet = PacketCodeC.INSTANCE.decode(requestBuf);
        if (!(packet instanceof LoginRequestPacket)) {
            System.out.println("不是登录请求，直接跳过");
            return;
        }

        LoginResponsePacket responsePacket = new LoginResponsePacket();
        responsePacket.setVersion(packet.getVersion());
        if (valid((LoginRequestPacket) packet)) {
            responsePacket.setSuccess(true);
            System.out.println("登录成功");
        } else {
            responsePacket.setSuccess(false);
            responsePacket.setReason("账号密码不对");
            System.out.println("登录失败");
        }

        ByteBuf responseBuf = PacketCodeC.INSTANCE.encode(ctx.alloc(), responsePacket);
        ctx.writeAndFlush(responseBuf);
    }

    private boolean valid(LoginRequestPacket packet) {
        return true;
    }

}
