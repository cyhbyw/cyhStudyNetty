package nia.chapter11;

import java.io.File;
import java.io.FileInputStream;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.DefaultFileRegion;
import io.netty.channel.FileRegion;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * Created by kerr.
 *
 * 代码清单 11-11 使用 FileRegion 传输文件的内容
 */
public class FileRegionWriteHandler extends ChannelInboundHandlerAdapter {
    private static final Channel CHANNEL_FROM_SOMEWHERE = new NioSocketChannel();
    private static final File FILE_FROM_SOMEWHERE = new File("");

    @Override
    public void channelActive(final ChannelHandlerContext ctx) throws Exception {
        File file = FILE_FROM_SOMEWHERE;
        Channel channel = CHANNEL_FROM_SOMEWHERE;
        FileInputStream fileInputStream = new FileInputStream(file);
        //以该文件的完整长度创建一个新的 DefaultFileRegion
        FileRegion fileRegion = new DefaultFileRegion(fileInputStream.getChannel(), 0, file.length());
        //发送该 DefaultFileRegion，并注册一个 ChannelFutureListener
        channel.writeAndFlush(fileRegion).addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) {
                if (!future.isSuccess()) {
                    //处理失败
                    Throwable cause = future.cause();
                    // Do something
                }
            }
        });
    }
}
