package com.luckk.lizzie;

import com.luckk.lizzie.handlers.SessionServerHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.*;

/**
 * @Author liukun.inspire
 * @Date 2023/3/8 09:21
 * @PackageName:com.luckk.lizzie
 * @ClassName: SessionChannelInitializer
 * @Version 1.0
 */
public class SessionChannelInitializer extends ChannelInitializer<SocketChannel> {
    public static final int BUFFER_SIZE = 1024 * 1024;

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        // 解析http解析
        pipeline.addLast(new HttpRequestDecoder());
        pipeline.addLast(new HttpResponseEncoder());
        // 用于拿到post请求的对象信息
        pipeline.addLast(new HttpObjectAggregator(BUFFER_SIZE));
        pipeline.addLast(new SessionServerHandler());

    }
}
