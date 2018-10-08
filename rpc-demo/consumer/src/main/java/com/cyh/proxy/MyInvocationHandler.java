package com.cyh.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.util.Arrays;

import com.cyh.consumer.ConsumeHandler;
import com.cyh.message.TransportMessage;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

/**
 * @author: CYH
 * @date: 2018/10/8 10:59
 */
public class MyInvocationHandler implements InvocationHandler {

    private Class<?> clazz;

    public MyInvocationHandler(Class<?> c) {
        this.clazz = c;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("InvocationHandler.invoke(): method=" + method + ", args=" + Arrays.toString(args));
        TransportMessage message = new TransportMessage();
        message.setInterfaceName(this.clazz.getName());
        message.setMethodName(method.getName());
        message.setParameterTypes(method.getParameterTypes());
        message.setArgs(args);

        final ConsumeHandler consumeHandler = new ConsumeHandler();
        EventLoopGroup group = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group).channel(NioSocketChannel.class).handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) {
                    ChannelPipeline pipeline = ch.pipeline();
                    pipeline.addLast("frameDecoder", new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));
                    pipeline.addLast("frameEncoder", new LengthFieldPrepender(4));
                    pipeline.addLast(new ObjectDecoder(ClassResolvers.cacheDisabled(null)));
                    pipeline.addLast(new ObjectEncoder());
                    pipeline.addLast(consumeHandler);
                }
            });
            ChannelFuture channelFuture = bootstrap.connect(new InetSocketAddress(8080)).sync();
            Channel channel = channelFuture.channel();
            System.out.println("客户端即将发送消息: " + message);
            channel.writeAndFlush(message).sync();
            channel.closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }
        return consumeHandler.getResponse();
    }
}
