package com.lmxdawn.user.dubbo.service;

import com.lmxdawn.dubboapi.res.MemberInfo;
import com.lmxdawn.dubboapi.service.UserService;
import com.lmxdawn.user.entity.Member;
import com.lmxdawn.user.service.MemberService;
import com.lmxdawn.user.service.UserLoginService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.BeanUtils;

import javax.annotation.Resource;

@DubboService
public class UserServiceImpl implements UserService {

    @Resource
    private MemberService memberService;

    @Resource
    private UserLoginService userLoginService;

    @Override
    public Long Login(String token) {
        return userLoginService.verify(token);
    }

    @Override
    public MemberInfo LoginInfo(String token) {
        Long uid = Login(token);
        // 获取用户信息
        Member member = memberService.findByUid(uid);
        MemberInfo memberInfo = new MemberInfo();
        BeanUtils.copyProperties(member, memberInfo);
        return memberInfo;
    }
}
