package com.cyh.client;

import java.util.UUID;

import com.cyh.protocol.command.Packet;
import com.cyh.protocol.command.PacketCodeC;
import com.cyh.protocol.request.LoginRequestPacket;
import com.cyh.protocol.response.LoginResponsePacket;
import com.cyh.protocol.response.MessageResponsePacket;
import com.cyh.utils.LoginUtil;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author: CYH
 * @date: 2018/10/8 18:42
 */
public class ClientHandler extends SimpleChannelInboundHandler {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("通道已经建立，客户端开始登录");
        LoginRequestPacket requestPacket = new LoginRequestPacket();
        requestPacket.setUserId(UUID.randomUUID().toString());
        requestPacket.setUsername("CYH");
        requestPacket.setPassword("123456");

        ByteBuf byteBuf = PacketCodeC.INSTANCE.encode(ctx.alloc(), requestPacket);
        ctx.writeAndFlush(byteBuf);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("收到服务端返回的消息 " + msg);
        ByteBuf byteBuf = (ByteBuf) msg;
        Packet packet = PacketCodeC.INSTANCE.decode(byteBuf);
        if (packet instanceof LoginResponsePacket) {
            handleForLogin(ctx, (LoginResponsePacket) packet);
        } else if (packet instanceof MessageResponsePacket) {
            handleForMessage((MessageResponsePacket) packet);
        }
        //ctx.close();
    }

    private void handleForLogin(ChannelHandlerContext ctx, LoginResponsePacket responsePacket) {
        if (responsePacket.getSuccess()) {
            System.out.println("客户端登录成功");
            LoginUtil.markAsLogin(ctx.channel());
        } else {
            System.out.println("客户端登录失败，原因是：" + responsePacket.getReason());
        }
    }

    private void handleForMessage(MessageResponsePacket responsePacket) {
        System.out.println("客户端收到服务端回复的消息：" + responsePacket.getMessage());
    }
}
