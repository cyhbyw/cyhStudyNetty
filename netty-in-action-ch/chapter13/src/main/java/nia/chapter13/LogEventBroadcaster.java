package nia.chapter13;

import java.io.File;
import java.io.RandomAccessFile;
import java.net.InetSocketAddress;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;

/**
 * 代码清单 13-3 LogEventBroadcaster
 *
 * @author <a href="mailto:norman.maurer@gmail.com">Norman Maurer</a>
 */
public class LogEventBroadcaster {
    private final EventLoopGroup group;
    private final Bootstrap bootstrap;
    private final File file;

    public LogEventBroadcaster(InetSocketAddress address, File file) {
        group = new NioEventLoopGroup();
        bootstrap = new Bootstrap();
        //引导该 NioDatagramChannel（无连接的）
        bootstrap.group(group).channel(NioDatagramChannel.class)
                //设置 SO_BROADCAST 套接字选项
                .option(ChannelOption.SO_BROADCAST, true).handler(new LogEventEncoder(address));
        this.file = file;
    }

    public void run() throws Exception {
        Channel channel = bootstrap.bind(0).sync().channel();
        long pointer = 0;
        for (;;) {
            long len = file.length();
            if (pointer > len) {
                //如果有必要，将文件指针设置到该文件的最后一个字节
                pointer = len;
            } else if (pointer < len) {
                RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r");
                //设置当前的文件指针，以确保没有任何的旧日志被发送
                randomAccessFile.seek(pointer);
                String line;
                while ((line = randomAccessFile.readLine()) != null) {
                    //对于每个日志条目，写入一个 LogEvent 到 Channel 中
                    channel.writeAndFlush(new LogEvent(null, -1, file.getAbsolutePath(), line));
                }
                //存储其在文件中的当前位置
                pointer = randomAccessFile.getFilePointer();
                randomAccessFile.close();
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.interrupted();
                break;
            }
        }
    }

    public void stop() {
        group.shutdownGracefully();
    }

    public static void main(String[] args) throws Exception {
        if (args.length != 2) {
            System.err.println("Need 2 argument: <port> <fileName>");
            System.exit(-1);
        }
        int port = Integer.parseInt(args[0]);
        File file = new File(args[1]);
        InetSocketAddress inetSocketAddress = new InetSocketAddress("255.255.255.255", port);
        LogEventBroadcaster broadcaster = new LogEventBroadcaster(inetSocketAddress, file);
        try {
            broadcaster.run();
        } finally {
            broadcaster.stop();
        }
    }
}
