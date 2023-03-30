package com.luckk.lizzie;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.sctp.nio.NioSctpServerChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.security.auth.callback.Callback;
import java.net.InetSocketAddress;
import java.util.concurrent.Callable;

/**
 * @Author liukun.inspire
 * @Date 2023/3/8 00:47
 * @PackageName:com.luckk.lizzie
 * @ClassName: SessionServer
 * @Version 1.0
 */
public class SessionServer implements Callable<Channel> {
    private final Logger logger = LoggerFactory.getLogger(SessionServer.class);


    private final EventLoopGroup boss = new NioEventLoopGroup(1);
    private final EventLoopGroup worker = new NioEventLoopGroup();

    public static final int PORT = 9090;

    private Channel channel;

    @Override
    public Channel call() {
        // ServerBootstrap serverBootstrap = new ServerBootstrap();
        // serverBootstrap.group(boss, worker)
        //         .option(ChannelOption.SO_BACKLOG, 128)
        //         .channel(NioServerSocketChannel.class)
        //         .childHandler(new SessionChannelInitializer());
        // ChannelFuture channelFuture = serverBootstrap.bind(new InetSocketAddress(PORT)).syncUninterruptibly();
        // this.channel = channelFuture.channel();
        //
        // return channel;
        ChannelFuture channelFuture = null;
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(boss, worker)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childHandler(new SessionChannelInitializer());

            channelFuture = b.bind(new InetSocketAddress(7397)).syncUninterruptibly();
            this.channel = channelFuture.channel();
        } catch (Exception e) {
            logger.error("socket server start error.", e);
        } finally {
            if (null != channelFuture && channelFuture.isSuccess()) {
                logger.info("socket server start done.");
            } else {
                logger.error("socket server start error.");
            }
        }
        return channel;
    }
}
