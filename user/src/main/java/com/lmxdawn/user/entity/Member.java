package com.lmxdawn.user.entity;

import lombok.Data;

@Data
public class Member {

    // 用户ID
    private Long uid;

    // 密码
    private String pwd;

    // 昵称
    private String name;

    // 头像
    private String avatar;

    // 签名
    private String remark;

}
