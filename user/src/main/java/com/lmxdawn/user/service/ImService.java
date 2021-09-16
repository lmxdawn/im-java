package com.lmxdawn.user.service;

import com.lmxdawn.dubboapi.res.ConnectionInfoDubboRes;
import com.lmxdawn.imcommon.req.ImMsgReq;

public interface ImService {

    boolean pushMsg(Long uid, ImMsgReq imMsgReq);

    ConnectionInfoDubboRes connectionInfo(Long uid);

}
