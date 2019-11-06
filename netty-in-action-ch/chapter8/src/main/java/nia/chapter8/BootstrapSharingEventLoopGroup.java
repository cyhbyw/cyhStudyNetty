package nia.chapter8;

import java.net.InetSocketAddress;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * 代码清单 8-5 引导服务器
 *
 * @author <a href="mailto:norman.maurer@gmail.com">Norman Maurer</a>
 * @author <a href="mailto:mawolfthal@gmail.com">Marvin Wolfthal</a>
 */
public class BootstrapSharingEventLoopGroup {

    /**
     * 代码清单 8-5 引导服务器
     * */
    public void bootstrap() {
        //创建 ServerBootstrap 以创建 ServerSocketChannel，并绑定它
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        //设置 EventLoopGroup，其将提供用以处理 Channel 事件的 EventLoop
        serverBootstrap.group(new NioEventLoopGroup(), new NioEventLoopGroup())
                //指定要使用的 Channel 实现
                .channel(NioServerSocketChannel.class)
                //设置用于处理已被接受的子 Channel 的 I/O 和数据的 ChannelInboundHandler
                .childHandler(new SimpleChannelInboundHandler<ByteBuf>() {
                    ChannelFuture connectFuture;

                    @Override
                    public void channelActive(ChannelHandlerContext ctx) {
                        //创建一个 Bootstrap 类的实例以连接到远程主机
                        Bootstrap bootstrap = new Bootstrap();
                        //指定 Channel 的实现
                        bootstrap.channel(NioSocketChannel.class).handler(new SimpleChannelInboundHandler<ByteBuf>() {
                            //为入站 I/O 设置 ChannelInboundHandler
                            @Override
                            protected void channelRead0(ChannelHandlerContext ctx, ByteBuf in) {
                                System.out.println("Received data");
                            }
                        });
                        /**
                         * 使用与分配给已被接受的子Channel相同的EventLoop
                         * 通过将已被接受的子Channel的EventLoop传递给Bootstrap的group()方法来共享该EventLoop
                         * 因为分配给EventLoop的所有Channel都使用同一个线程，所以这避免了额外的线程创建以及相关的上下文切换
                         */
                        bootstrap.group(ctx.channel().eventLoop());
                        //连接到远程节点
                        connectFuture = bootstrap.connect(new InetSocketAddress("www.manning.com", 80));
                    }

                    @Override
                    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) {
                        if (connectFuture.isDone()) {
                            //当连接完成时，执行一些数据操作（如代理）
                            // do something with the data
                        }
                    }
                });
        //通过配置好的 ServerBootstrap 绑定该 ServerSocketChannel
        ChannelFuture future = serverBootstrap.bind(new InetSocketAddress(8080));
        future.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture channelFuture) {
                if (channelFuture.isSuccess()) {
                    System.out.println("Server bound");
                } else {
                    System.err.println("Bind attempt failed");
                    channelFuture.cause().printStackTrace();
                }
            }
        });
    }
}
