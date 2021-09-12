package com.lmxdawn.user.service.impl;

import com.lmxdawn.user.dao.MemberDao;
import com.lmxdawn.user.entity.Member;
import com.lmxdawn.user.service.MemberService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class MemberServiceImpl implements MemberService {

    @Resource
    private MemberDao memberDao;

    @Override
    public Member findByUid(Long uid) {
        return memberDao.findByUid(uid);
    }

    @Override
    public Member findByTel(String tel) {
        return memberDao.findByTel(tel);
    }
}
