package com.lmxdawn.common.constant;

/**
 * redis 常量
 */
public interface CacheConstant {

    String ADMIN_AUTH_RULES = "admin_auth_rules:%s"; // 管理员的权限

    String IM_ROUTE_UID = "imrp:"; // IM路由服务保存的用户key前缀

    String USER_LOGIN = "ul:%s"; // 用户登录

}
