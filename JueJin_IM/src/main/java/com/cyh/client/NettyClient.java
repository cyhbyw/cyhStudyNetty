package com.cyh.client;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import com.cyh.protocol.command.PacketCodeC;
import com.cyh.protocol.request.MessageRequestPacket;
import com.cyh.utils.LoginUtil;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author: CYH
 * @date: 2018/10/8 18:41
 */
public class NettyClient {

    public static void main(String[] args) throws Exception {
        NioEventLoopGroup worker = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(worker).channel(NioSocketChannel.class).option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                    .option(ChannelOption.SO_KEEPALIVE, true).option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<NioSocketChannel>() {
                        @Override
                        protected void initChannel(NioSocketChannel ch) {
                            ch.pipeline().addLast(new ClientHandler());
                        }
                    });
            ChannelFuture channelFuture = connect(bootstrap);
            //channelFuture.channel().closeFuture().sync();
        } finally {
            //worker.shutdownGracefully();
        }
    }

    private static ChannelFuture connect(Bootstrap bootstrap) {
        return bootstrap.connect("localhost", 8000).addListener(future -> {
            if (future.isSuccess()) {
                Channel channel = ((ChannelFuture) future).channel();
                startConsoleThread(channel);
            }
        });
    }

    private static void startConsoleThread(Channel channel) {
        new Thread(() -> {
            while (!Thread.interrupted()) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (LoginUtil.hasLogin(channel)) {
                    System.out.print("请输入需要发送到服务端的消息：");
                    Scanner scanner = new Scanner(System.in);
                    String line = scanner.nextLine();
                    MessageRequestPacket requestPacket = new MessageRequestPacket();
                    requestPacket.setMessage(line);
                    ByteBuf byteBuf = PacketCodeC.INSTANCE.encode(channel.alloc(), requestPacket);
                    channel.writeAndFlush(byteBuf);
                }
            }
        }).start();
    }

}
