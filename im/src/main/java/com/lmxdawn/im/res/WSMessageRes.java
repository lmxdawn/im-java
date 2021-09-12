package com.lmxdawn.im.res;

import lombok.Data;

/**
 * websocket 消息实体
 */
@Data
public class WSMessageRes {

    /**
     * 消息类型
     */
    private Integer msgType;

    /**
     * 消息内容
     */
    private String msgContent;

}