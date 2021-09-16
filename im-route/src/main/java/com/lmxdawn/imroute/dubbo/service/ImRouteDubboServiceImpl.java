package com.lmxdawn.imroute.dubbo.service;

import com.alibaba.fastjson.JSON;
import com.lmxdawn.dubboapi.res.ConnectionInfoDubboRes;
import com.lmxdawn.dubboapi.service.ImRouteDubboService;
import com.lmxdawn.imroute.res.LoadBalancingIpRes;
import com.lmxdawn.imroute.service.LoadBalancingService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import static com.lmxdawn.common.constant.CacheConstant.IM_ROUTE_UID;

@DubboService
public class ImRouteDubboServiceImpl implements ImRouteDubboService {

    @Autowired
    private LoadBalancingService loadBalancingService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public ConnectionInfoDubboRes connectionLogin(Long uid) {

        ConnectionInfoDubboRes connectionInfoDubboRes = new ConnectionInfoDubboRes();

        // 负载均衡
        LoadBalancingIpRes loadBalancingIpRes = loadBalancingService.imIpHash(uid);
        connectionInfoDubboRes.setIp(loadBalancingIpRes.getIp());
        connectionInfoDubboRes.setHttpPort(loadBalancingIpRes.getHttpPort());
        connectionInfoDubboRes.setWsPort(loadBalancingIpRes.getWsPort());
        String key = IM_ROUTE_UID + uid;

        String value = JSON.toJSONString(connectionInfoDubboRes);
        redisTemplate.opsForValue().set(key, value);

        return connectionInfoDubboRes;
    }

    @Override
    public ConnectionInfoDubboRes connectionInfo(Long uid) {
        String key = IM_ROUTE_UID + uid;
        String value = redisTemplate.opsForValue().get(key);
        return JSON.parseObject(value, ConnectionInfoDubboRes.class);
    }
}
