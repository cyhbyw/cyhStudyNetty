package com.cyh;

import java.time.LocalDateTime;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BusinessHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    public BusinessHandler() {
        log.debug("BusinessHandler()...");
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, FullHttpRequest httpRequest) throws Exception {
        String path = httpRequest.uri();
        String body = httpRequest.content().toString(CharsetUtil.UTF_8);
        HttpMethod method = httpRequest.method();
        log.debug("method={}, path={}, body={}", method, path, body);

        if (HttpMethod.GET.equals(method)) {
            String result = "Get request, Response=" + LocalDateTime.now();
            send(result, ctx, HttpResponseStatus.OK);
        } else if (HttpMethod.POST.equals(method)) {
            // TODO
        }
    }

    private void send(String content, ChannelHandlerContext ctx, HttpResponseStatus status) {
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, status,
                Unpooled.copiedBuffer(content, CharsetUtil.UTF_8));
        response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain;charset=UTF-8");
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }


}
