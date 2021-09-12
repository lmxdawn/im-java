package com.lmxdawn.im.ws;

import com.alibaba.fastjson.JSON;
import com.lmxdawn.imcommon.constant.WSReqTypeConstant;
import com.lmxdawn.imcommon.constant.WSResTypeConstant;
import com.lmxdawn.im.req.WSBaseReq;
import com.lmxdawn.im.res.WSBaseRes;
import com.lmxdawn.im.service.UserLoginService;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@ChannelHandler.Sharable
@Slf4j
@Component
public class WSServerHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    @Autowired
    private UserLoginService userLoginService;

    private static WSServerHandler wsServerHandler;

    @PostConstruct
    public void init() {
        wsServerHandler = this;
    }

    /**
     * 取消绑定
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        // 可能出现业务判断离线后再次触发 channelInactive
        log.warn("触发 channelInactive 掉线![{}]", ctx.channel().id());
        userOffLine(ctx);
    }

    /**
     * 心跳检查
     * @param ctx
     * @param evt
     * @throws Exception
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent idleStateEvent = (IdleStateEvent) evt;
            // 读空闲
            if (idleStateEvent.state() == IdleState.READER_IDLE) {
                // 关闭用户的连接
                userOffLine(ctx);
            }
        }
        super.userEventTriggered(ctx, evt);
    }

    /**
     * 用户下线
     */
    private void userOffLine(ChannelHandlerContext ctx) {
        WSSocketHolder.remove(ctx.channel());
        ctx.channel().close();
    }

    /**
     * 读到客户端的内容 （这里只做心跳检查）
     * @param ctx
     * @param msg
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) {
        WSBaseReq wsBaseReq = JSON.parseObject(msg.text(), WSBaseReq.class);
        Long group = wsBaseReq.getGroup();
        if (group == WSReqTypeConstant.PING) { // 心跳
            log.info("客户端心跳");
        } else if (group == WSReqTypeConstant.LOGIN) { // 登录类型
            log.info("用户登录");
            String token = wsBaseReq.getToken();
            userLogin(ctx, token);
        } else {
            log.info("未知类型");
        }

    }

    private void userLogin(ChannelHandlerContext ctx, String token) {
        Long loginUid = wsServerHandler.userLoginService.Login(token);
        if (loginUid == null) {
            log.info("非法登录: {}", token);
            // 登录异常, 发送下线通知
            WSBaseRes wsBaseRes = new WSBaseRes();
            wsBaseRes.setType(WSResTypeConstant.LOGIN_OUT);

            String s = JSON.toJSONString(wsBaseRes);

            // 发送下线消息
            ctx.channel().writeAndFlush(new TextWebSocketFrame(s));
            ctx.channel().close();
            return;
        }

        // 判断是否在线, 如果在线, 则剔除当前在线用户
        Channel channel = WSSocketHolder.get(loginUid);
        // 如果不是第一次登陆, 并且 客户端ID和当前的不匹配, 则通知之前的客户端下线
        if (channel != null && !ctx.channel().id().equals(channel.id())) {
            WSBaseRes wsBaseRes = new WSBaseRes();
            wsBaseRes.setType(WSResTypeConstant.WS_OUT);

            String s = JSON.toJSONString(wsBaseRes);

            // 发送下线消息
            channel.writeAndFlush(new TextWebSocketFrame(s));
        }

        // 加入 在线 map 中
        WSSocketHolder.put(loginUid, ctx.channel());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        if ("Connection reset by peer".equals(cause.getMessage())) {
            log.error("连接出现问题");
            return;
        }

        log.error(cause.getMessage(), cause);
    }

}
