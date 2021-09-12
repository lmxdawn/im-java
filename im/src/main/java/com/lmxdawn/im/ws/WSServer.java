package com.lmxdawn.im.ws;

import com.alibaba.fastjson.JSON;
import com.lmxdawn.im.res.WSBaseRes;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.net.InetSocketAddress;

@Component
@Slf4j
public class WSServer {

    @Value("${ws.port}")
    private int wsPort;

    private final EventLoopGroup boss = new NioEventLoopGroup();
    private final EventLoopGroup work = new NioEventLoopGroup();

    /**
     * 启动 ws server
     *
     * @return
     * @throws InterruptedException
     */
    @PostConstruct
    public void start() throws InterruptedException {

        ServerBootstrap bootstrap = new ServerBootstrap()
                .group(boss, work)
                .channel(NioServerSocketChannel.class)
                .localAddress(new InetSocketAddress(wsPort))
                //保持长连接
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childHandler(new WSServerInitializer());

        ChannelFuture future = bootstrap.bind().sync();
        if (future.isSuccess()) {
            log.info("启动 ws server 成功");
        }
    }

    /**
     * 销毁
     */
    @PreDestroy
    public void destroy() {
        boss.shutdownGracefully().syncUninterruptibly();
        work.shutdownGracefully().syncUninterruptibly();
        log.info("关闭 ws server 成功");
    }

    /**
     * 发送 Google Protocol 编码消息
     * @param fromUid 发送给谁
     * @param wsBaseRes 消息
     * @return
     */
    public Boolean sendMsg(Long fromUid, WSBaseRes wsBaseRes) {
        Channel channel = WSSocketHolder.get(fromUid);

        if (null == channel) {
            log.info("用户ID[" + fromUid + "]不在线！");
            return false;
        }

        String s = JSON.toJSONString(wsBaseRes);

        channel.writeAndFlush(new TextWebSocketFrame(s));
        return true;
    }

}
