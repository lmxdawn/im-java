package com.lmxdawn.im.res;

import lombok.Data;

/**
 * websocket 通信的用户信息
 */
@Data
public class WSUserRes {
    /**
     * 用户id
     */
    private Long uid;
    /**
     * 用户昵称
     */
    private String name;
    /**
     * 用户头像
     */
    private String avatar;
    /**
     * 个性签名
     */
    private String remark;

}
