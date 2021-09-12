package com.lmxdawn.common.enums;

import lombok.Getter;

/**
 * 返回结果的枚举类
 */
@Getter
public enum ResultEnum {

    SUCCESS(0, "success"),
    NOT_NETWORK(1, "The system is busy, please try again later."), // 系统繁忙，请稍后再试。
    LOGIN_VERIFY_FALL(2, "Login invalid"), // 登录失效
    PARAM_VERIFY_FALL(3, "Parameter validation error"), // 参数验证错误
    AUTH_FAILED(4, "Permission verification failed"), // 权限验证失败
    DATA_NOT(5, "No relevant data"), // 没有相关数据
    DATA_CHANGE(6, "No changes to the data"), // 数据没有任何更改
    DATA_REPEAT(7, "Data already exists"), // 数据已存在

    USER_LOGIN_PWD_ERR(10000, "Password error"), // 密码错误
    USER_MSG_ERR(10001, "Failed to send message"), // 密码错误

    IM_ROUTE_USER_NOT(20000, "User is not logged in"), // 用户未登录

    ;

    private Integer code;

    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
