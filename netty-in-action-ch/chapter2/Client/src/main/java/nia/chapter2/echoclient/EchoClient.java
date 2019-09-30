package nia.chapter2.echoclient;

import java.net.InetSocketAddress;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

/**
 * 代码清单 2-4 客户端的主类
 *
 * @author <a href="mailto:norman.maurer@gmail.com">Norman Maurer</a>
 */
@Slf4j
public class EchoClient {

    public static void main(String[] args) throws Exception {
        new EchoClient().start("localhost", 8080);
    }

    public void start(String host, int port) throws Exception {
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        try {
            //创建 Bootstrap
            Bootstrap bootstrap = new Bootstrap();
            //指定 EventLoopGroup 以处理客户端事件；需要适用于 NIO 的实现
            bootstrap.group(eventLoopGroup)
                    //适用于 NIO 传输的Channel 类型
                    .channel(NioSocketChannel.class)
                    //设置服务器的InetSocketAddress
                    .remoteAddress(new InetSocketAddress(host, port))
                    //在创建Channel时，向 ChannelPipeline中添加一个 EchoClientHandler实例
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel channel) {
                            channel.pipeline().addLast(new EchoClientHandler());
                        }
                    });
            //连接到远程节点，阻塞等待直到连接完成
            ChannelFuture channelFuture = bootstrap.connect().sync();
            //阻塞，直到Channel 关闭
            channelFuture.channel().closeFuture().sync();
        } finally {
            //关闭线程池并且释放所有的资源
            eventLoopGroup.shutdownGracefully().sync();
        }
    }
}

