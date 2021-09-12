package com.lmxdawn.user.service.impl;

import com.alibaba.fastjson.JSON;
import com.lmxdawn.dubboapi.res.ConnectionInfoRes;
import com.lmxdawn.dubboapi.service.ImRouteService;
import com.lmxdawn.imcommon.req.ImMsgReq;
import com.lmxdawn.user.service.ImService;
import com.lmxdawn.user.util.OkHttpUtil;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;

@Service
public class ImServiceImpl implements ImService {

    @DubboReference
    private ImRouteService imRouteService;

    @Override
    public boolean pushMsg(Long uid, ImMsgReq imMsgReq) {

        ConnectionInfoRes connectionInfoRes = imRouteService.connectionInfo(uid);
        if (connectionInfoRes == null || "".equals(connectionInfoRes.getIp()) || "".equals(connectionInfoRes.getHttpPort())) {
            return false;
        }
        String url = "http://" + connectionInfoRes.getIp() + ":" + connectionInfoRes.getHttpPort() + "/msg/push";
        String json = JSON.toJSONString(imMsgReq);
        String s = OkHttpUtil.postJson(url, json);

        // false为用户不在线
        return "true".equals(s);
    }

    @Override
    public ConnectionInfoRes connectionInfo(Long uid) {
        return imRouteService.connectionLogin(uid);
    }
}
