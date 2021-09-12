package com.lmxdawn.user.service;

import com.lmxdawn.dubboapi.res.ConnectionInfoRes;
import com.lmxdawn.imcommon.req.ImMsgReq;

public interface ImService {

    boolean pushMsg(Long uid, ImMsgReq imMsgReq);

    ConnectionInfoRes connectionInfo(Long uid);

}
