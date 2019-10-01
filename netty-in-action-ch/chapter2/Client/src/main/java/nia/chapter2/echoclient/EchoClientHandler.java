package nia.chapter2.echoclient;

import java.time.LocalDateTime;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 代码清单 2-3 客户端的 ChannelHandler
 *
 * @author <a href="mailto:norman.maurer@gmail.com">Norman Maurer</a>
 */
//标记该类的实例可以被多个 Channel 共享
@Sharable
@Slf4j
public class EchoClientHandler extends SimpleChannelInboundHandler<ByteBuf> {

    public EchoClientHandler() {
        log.debug("EchoClientHandler()");
    }

    /**
     * 当与服务器建立连接之后被调用
     * @param ctx
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        //当被通知 Channel是活跃的时候，发送一条消息
        String msg = "RequestTime=" + LocalDateTime.now();
        log.debug("channelActive(). Client will send msg: " + msg);
        ctx.writeAndFlush(Unpooled.copiedBuffer(msg, CharsetUtil.UTF_8));
    }

    /**
     * 当从服务器接收到一条消息时被调用
     * @param ctx
     * @param in
     */
    @Override
    public void channelRead0(ChannelHandlerContext ctx, ByteBuf in) {
        //记录已接收消息的转储
        log.debug("Client received: " + in.toString(CharsetUtil.UTF_8));
    }

    /**
     * 在发生异常时，记录错误并关闭Channel
     * @param ctx
     * @param cause
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
