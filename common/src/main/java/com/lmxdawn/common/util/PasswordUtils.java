package com.lmxdawn.common.util;

import org.springframework.util.DigestUtils;

/**
 * 密码相关的工具类
 */
public class PasswordUtils {

    public static String authAdminPwd(String pwd) {
        return DigestUtils.md5DigestAsHex(DigestUtils.md5DigestAsHex(pwd.getBytes()).getBytes());
    }

    public static String memberPwd(String pwd) {
        return DigestUtils.md5DigestAsHex(DigestUtils.md5DigestAsHex(pwd.getBytes()).getBytes());
    }

}
