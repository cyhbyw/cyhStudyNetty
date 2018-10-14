package com.cyh.client;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import com.cyh.client.console.ConsoleCommandManager;
import com.cyh.client.console.LoginConsoleCommand;
import com.cyh.client.handler.ClientCreateGroupHandler;
import com.cyh.client.handler.ClientJoinGroupHandler;
import com.cyh.client.handler.ClientListGroupMembersHandler;
import com.cyh.client.handler.ClientLoginHandler;
import com.cyh.client.handler.ClientMessageHandler;
import com.cyh.client.handler.ClientQuitGroupHandler;
import com.cyh.codec.PacketDecoder;
import com.cyh.codec.PacketEncoder;
import com.cyh.codec.Splitter;
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
                            pipeline.addLast(new ClientCreateGroupHandler());
                            pipeline.addLast(new ClientJoinGroupHandler());
                            pipeline.addLast(new ClientQuitGroupHandler());
                            pipeline.addLast(new ClientListGroupMembersHandler());
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
        LoginConsoleCommand loginConsoleCommand = new LoginConsoleCommand();

        new Thread(() -> {
            while (!Thread.interrupted()) {
                sleep();
                if (!SessionUtil.hasLogin(channel)) {
                    loginConsoleCommand.execute(scanner, channel);
                } else {
                    ConsoleCommandManager.execute(scanner, channel);
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
