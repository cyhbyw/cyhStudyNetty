package nia.chapter10;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.TooLongFrameException;

/**
 * 代码清单 10-4 TooLongFrameException
 *
 * @author <a href="mailto:norman.maurer@gmail.com">Norman Maurer</a>
 */
public class SafeByteToMessageDecoder extends ByteToMessageDecoder {
    private static final int MAX_FRAME_SIZE = 1024;

    @Override
    public void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
        int readable = in.readableBytes();
        //检查缓冲区中是否有超过 MAX_FRAME_SIZE 个字节
        if (readable > MAX_FRAME_SIZE) {
            //跳过所有的可读字节，抛出 TooLongFrameException 并通知 ChannelHandler
            in.skipBytes(readable);
            throw new TooLongFrameException("Frame too big!");
        }
        // do something
        // ...
    }
}
