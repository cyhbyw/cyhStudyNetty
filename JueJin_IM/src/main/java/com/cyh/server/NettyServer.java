package com.cyh.server;

import com.cyh.codec.PacketDecoder;
import com.cyh.codec.PacketEncoder;
import com.cyh.codec.Splitter;
import com.cyh.server.handler.AuthHandler;
import com.cyh.server.handler.LifeCyCleTestHandler;
import com.cyh.server.handler.ServerLoginHandler;
import com.cyh.server.handler.ServerMessageHandler;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: CYH
 * @date: 2018/10/8 18:24
 */
@Slf4j
public class NettyServer {

    public static void main(String[] args) throws Exception {
        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(boss, worker).channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 1024).childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childOption(ChannelOption.TCP_NODELAY, true)
                    .childHandler(new ChannelInitializer<NioSocketChannel>() {
                        @Override
                        protected void initChannel(NioSocketChannel ch) {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new LifeCyCleTestHandler());
                            pipeline.addLast(new Splitter());
                            ////// 这个顺序与教程中的不一样，需要去理解这是为什么？
                            pipeline.addLast(new PacketDecoder());
                            pipeline.addLast(new PacketEncoder());
                            pipeline.addLast(new ServerLoginHandler());
                            pipeline.addLast(new AuthHandler());
                            pipeline.addLast(new ServerMessageHandler());
                        }
                    });
            ChannelFuture channelFuture = bind(serverBootstrap, 8000);
            channelFuture.channel().closeFuture().sync();
        } finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }

    private static ChannelFuture bind(ServerBootstrap serverBootstrap, int port) throws Exception {
        ChannelFuture channelFuture = serverBootstrap.bind(port).sync();
        channelFuture.addListener(future -> {
            if (future.isSuccess()) {
                log.debug("服务器启动成功，端口: " + port);
            } else {
                System.err.println("Failed，端口: " + port);
            }
        });
        return channelFuture;
    }

}
