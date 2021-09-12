package com.lmxdawn.im.req;

import lombok.Data;

/**
 * websocket 通信的类
 */
@Data
public class WSBaseReq {

    /**
     * 消息分组
     */
    private Long group;

    /**
     * 用户id
     */
    private Integer uid;

    /**
     * 登录token
     */
    private String token;

}
