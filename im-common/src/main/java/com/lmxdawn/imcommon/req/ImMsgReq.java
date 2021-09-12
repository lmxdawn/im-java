package com.lmxdawn.imcommon.req;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class ImMsgReq {

    /**
     * 消息分组
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
     * 消息类型
     */
    private Integer msgType;

    /**
     * 消息内容
     */
    private String msgContent;

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
