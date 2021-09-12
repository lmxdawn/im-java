package com.lmxdawn.im.res;

import lombok.Data;

/**
 * websocket 通信的类
 */
@Data
public class WSBaseRes {

    /**
     * 类型
     */
    private Integer type;

    /**
     * 群组ID
     */
    private Long gid;

    /**
     * 接收者ID
     */
    private Long receiveId;

    /**
     * 消息实体
     */
    private WSMessageRes message;

    /**
     * 发送者用户信息
     */
    private WSUserRes user;

}
