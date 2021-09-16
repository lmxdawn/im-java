package com.lmxdawn.user.dubbo.service;

import com.lmxdawn.dubboapi.service.UserDubboService;
import com.lmxdawn.user.service.MemberService;
import com.lmxdawn.user.service.LoginService;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;

@DubboService
public class UserDubboServiceImpl implements UserDubboService {

    @Resource
    private MemberService memberService;

    @Resource
    private LoginService loginService;

    @Override
    public Long Login(String token) {
        return loginService.verify(token);
    }
}
