package com.lmxdawn.dubboapi.res;

import lombok.Data;

import java.io.Serializable;

@Data
public class MemberInfo implements Serializable {

    // 用户ID
    private Long uid;

    // 昵称
    private String name;

    // 头像
    private String avatar;

    // 签名
    private String remark;
}
