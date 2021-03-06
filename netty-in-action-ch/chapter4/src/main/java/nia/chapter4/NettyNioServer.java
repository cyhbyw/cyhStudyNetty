package nia.chapter4;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * 代码清单 4-4 使用 Netty 的异步网络处理
 *
 * @author <a href="mailto:norman.maurer@gmail.com">Norman Maurer</a>
 */
public class NettyNioServer {

    public void server(int port) throws Exception {
        final ByteBuf buf = Unpooled.unreleasableBuffer(Unpooled.copiedBuffer("Hi!\r\n", Charset.forName("UTF-8")));
        //为非阻塞模式使用NioEventLoopGroup
        NioEventLoopGroup group = new NioEventLoopGroup();
        try {
            //创建ServerBootstrap
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(group)
                    //使用 NioServerSocketChannel 非阻塞模式
                    .channel(NioServerSocketChannel.class).localAddress(new InetSocketAddress(port))
                    //指定 ChannelInitializer，对于每个已接受的连接都调用它
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel socketChannel) {
                            socketChannel.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                                //添加 ChannelInboundHandlerAdapter以接收和处理事件
                                @Override
                                public void channelActive(ChannelHandlerContext ctx) {
                                    //将消息写到客户端，并添加ChannelFutureListener，以便消息一被写完就关闭连接
                                    ctx.writeAndFlush(buf.duplicate()).addListener(ChannelFutureListener.CLOSE);
                                }
                            });
                        }
                    });
            //绑定服务器以接受连接
            ChannelFuture channelFuture = serverBootstrap.bind().sync();
            channelFuture.channel().closeFuture().sync();
        } finally {
            //释放所有的资源
            group.shutdownGracefully().sync();
        }
    }
}

