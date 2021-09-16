package com.lmxdawn.user.service.impl;

import com.alibaba.fastjson.JSON;
import com.lmxdawn.dubboapi.res.ConnectionInfoDubboRes;
import com.lmxdawn.dubboapi.service.ImRouteDubboService;
import com.lmxdawn.imcommon.req.ImMsgReq;
import com.lmxdawn.user.service.ImService;
import com.lmxdawn.user.util.OkHttpUtil;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;

@Service
public class ImServiceImpl implements ImService {

    @DubboReference
    private ImRouteDubboService imRouteDubboService;

    @Override
    public boolean pushMsg(Long uid, ImMsgReq imMsgReq) {

        ConnectionInfoDubboRes connectionInfoDubboRes = imRouteDubboService.connectionInfo(uid);
        if (connectionInfoDubboRes == null || "".equals(connectionInfoDubboRes.getIp()) || "".equals(connectionInfoDubboRes.getHttpPort())) {
            return false;
        }
        String url = "http://" + connectionInfoDubboRes.getIp() + ":" + connectionInfoDubboRes.getHttpPort() + "/msg/push";
        String json = JSON.toJSONString(imMsgReq);
        String s = OkHttpUtil.postJson(url, json);

        // false为用户不在线
        return "true".equals(s);
    }

    @Override
    public ConnectionInfoDubboRes connectionInfo(Long uid) {
        return imRouteDubboService.connectionLogin(uid);
    }
}
