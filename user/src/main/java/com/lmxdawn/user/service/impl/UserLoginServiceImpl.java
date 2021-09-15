package com.lmxdawn.user.service.impl;

import com.lmxdawn.common.util.JwtUtils;
import com.lmxdawn.user.service.UserLoginService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static com.lmxdawn.common.constant.CacheConstant.USER_LOGIN;

@Service
public class UserLoginServiceImpl implements UserLoginService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public String createToken(Long uid) {
        String key = String.format(USER_LOGIN, uid);
        String token = redisTemplate.opsForValue().get(key);
        if (token == null || token.isEmpty()) {
            Map<String, Object> claims = new HashMap<>();
            claims.put("uid", uid);
            token = JwtUtils.createToken(claims);
            redisTemplate.opsForValue().set(key, token);
        }
        return token;
    }

    @Override
    public Long verify(String token) {
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

        String key = String.format(USER_LOGIN, uid);
        String s = redisTemplate.opsForValue().get(key);
        if (s == null || s.isEmpty()) {
            return null;
        }
        return uid;
    }
}
