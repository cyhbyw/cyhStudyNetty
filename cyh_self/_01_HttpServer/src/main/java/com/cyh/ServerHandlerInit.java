package com.cyh;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpContentCompressor;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;

public class ServerHandlerInit extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        pipeline.addLast("encode", new HttpResponseEncoder());

        pipeline.addLast("decode", new HttpRequestDecoder());
        // 聚合http请求
        pipeline.addLast("aggregator", new HttpObjectAggregator(10 * 1024 * 1024));
        // 启用http压缩
        pipeline.addLast("compressor", new HttpContentCompressor());
        // 自己的业务处理
        pipeline.addLast("business", new BusinessHandler());
    }
}
