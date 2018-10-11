package com.cyh.server;

import com.cyh.protocol.command.Packet;
import com.cyh.protocol.command.PacketCodeC;
import com.cyh.protocol.request.LoginRequestPacket;
import com.cyh.protocol.request.MessageRequestPacket;
import com.cyh.protocol.response.LoginResponsePacket;
import com.cyh.protocol.response.MessageResponsePacket;

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
        System.out.println("收到客户端发来的消息：" + msg);
        ByteBuf requestBuf = (ByteBuf) msg;
        Packet packet = PacketCodeC.INSTANCE.decode(requestBuf);
        if (packet instanceof LoginRequestPacket) {
            handleForLogin(ctx, (LoginRequestPacket) packet);
        } else if (packet instanceof MessageRequestPacket) {
            handleForMessage(ctx, (MessageRequestPacket) packet);
        }
    }

    private void handleForLogin(ChannelHandlerContext ctx, LoginRequestPacket loginRequestPacket) {
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

        ByteBuf responseBuf = PacketCodeC.INSTANCE.encode(ctx.alloc(), responsePacket);
        ctx.writeAndFlush(responseBuf);
    }

    private boolean valid(LoginRequestPacket packet) {
        return true;
    }

    private void handleForMessage(ChannelHandlerContext ctx, MessageRequestPacket messageRequestPacket) {
        System.out.println("客户端说：" + messageRequestPacket.getMessage());
        MessageResponsePacket responsePacket = new MessageResponsePacket();
        responsePacket.setMessage("服务端回复【" + messageRequestPacket.getMessage() + "】");
        ByteBuf byteBuf = PacketCodeC.INSTANCE.encode(ctx.alloc(), responsePacket);
        ctx.writeAndFlush(byteBuf);
    }

}
