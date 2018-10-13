package com.cyh.client;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import com.cyh.client.handler.ClientLoginHandler;
import com.cyh.client.handler.ClientMessageHandler;
import com.cyh.codec.PacketDecoder;
import com.cyh.codec.PacketEncoder;
import com.cyh.codec.Splitter;
import com.cyh.protocol.request.LoginRequestPacket;
import com.cyh.protocol.request.MessageRequestPacket;
import com.cyh.utils.SessionUtil;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
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
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new Splitter());
                            ////// 这个顺序与教程中的不一样，需要去理解这是为什么？
                            pipeline.addLast(new PacketDecoder());
                            pipeline.addLast(new PacketEncoder());
                            pipeline.addLast(new ClientLoginHandler());
                            pipeline.addLast(new ClientMessageHandler());
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
        Scanner scanner = new Scanner(System.in);

        new Thread(() -> {
            while (!Thread.interrupted()) {
                sleep();
                if (!SessionUtil.hasLogin(channel)) {
                    System.out.print("输入用户名登录: ");
                    LoginRequestPacket requestPacket = new LoginRequestPacket();
                    requestPacket.setUsername(scanner.nextLine());
                    requestPacket.setPassword("123456");
                    channel.writeAndFlush(requestPacket);
                } else {
                    System.out.print("请输入<toUserId> <message>: ");
                    String toUserId = scanner.next();
                    String message = scanner.next();
                    MessageRequestPacket requestPacket = new MessageRequestPacket(toUserId, message);
                    channel.writeAndFlush(requestPacket);
                }
            }
        }).start();
    }

    private static void sleep() {
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
