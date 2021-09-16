package com.lmxdawn.dubboapi.service;

import com.lmxdawn.dubboapi.res.ConnectionInfoDubboRes;

public interface ImRouteDubboService {

    ConnectionInfoDubboRes connectionLogin(Long uid);

    ConnectionInfoDubboRes connectionInfo(Long uid);

}
