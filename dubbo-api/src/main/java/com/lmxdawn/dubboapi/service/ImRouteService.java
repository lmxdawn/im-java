package com.lmxdawn.dubboapi.service;

import com.lmxdawn.dubboapi.res.ConnectionInfoRes;
import com.lmxdawn.dubboapi.res.MemberInfo;

public interface ImRouteService {

    ConnectionInfoRes connectionLogin(Long uid);

    ConnectionInfoRes connectionInfo(Long uid);

}
