package com.lmxdawn.user.service;

public interface UserLoginService {
    String createToken(Long uid);
    Long verify(String token);
}
