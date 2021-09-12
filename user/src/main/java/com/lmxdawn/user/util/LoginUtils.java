package com.lmxdawn.user.util;

import com.lmxdawn.common.util.JwtUtils;
import io.jsonwebtoken.Claims;

import java.util.HashMap;
import java.util.Map;

public class LoginUtils {

    public static String createToken(Long uid) {

        Map<String, Object> claims = new HashMap<>();
        claims.put("uid", uid);
        return JwtUtils.createToken(claims); // 一天后过期
    }

    public static Long verify(String token) {

        // 验证 token
        Claims claims = JwtUtils.parse(token);
        if (claims == null) {
            return null;
        }

        long uid;
        try {
            uid = Long.parseLong(claims.get("uid").toString());
        }catch (Exception e) {
            return null;
        }
        if (uid <= 0) {
            return null;
        }

        return uid;
    }

}
