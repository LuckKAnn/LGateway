package com.luckk.lizzie;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @Author liukun.inspire
 * @Date 2023/3/30 16:00
 * @PackageName: com.luckk.lizzie
 * @ClassName: BaseHandler
 * @Version 1.0
 */
public abstract class BaseHandler<T> extends SimpleChannelInboundHandler<T> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, T msg) throws Exception {
        session(channelHandlerContext, channelHandlerContext.channel(), msg);
    }

    protected abstract void session(ChannelHandlerContext ctx, Channel channel, T msg);
}
