package com.lmxdawn.dubboapi.service;

import com.lmxdawn.dubboapi.res.MemberInfo;

/**
 *
 */
public interface UserService {

    Long Login(String token);


    MemberInfo LoginInfo(String token);

}
