package com.lmxdawn.imroute.dubbo.service;

import com.alibaba.fastjson.JSON;
import com.lmxdawn.dubboapi.res.ConnectionInfoRes;
import com.lmxdawn.dubboapi.service.ImRouteService;
import com.lmxdawn.imroute.res.LoadBalancingIpRes;
import com.lmxdawn.imroute.service.LoadBalancingService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import static com.lmxdawn.common.constant.CacheConstant.IM_ROUTE_UIP;

@DubboService
public class ImRouteServiceImpl implements ImRouteService {

    @Autowired
    private LoadBalancingService loadBalancingService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public ConnectionInfoRes connectionLogin(Long uid) {

        ConnectionInfoRes connectionInfoRes = new ConnectionInfoRes();

        // 负载均衡
        LoadBalancingIpRes loadBalancingIpRes = loadBalancingService.imIpHash(uid);
        connectionInfoRes.setIp(loadBalancingIpRes.getIp());
        connectionInfoRes.setHttpPort(loadBalancingIpRes.getHttpPort());
        connectionInfoRes.setWsPort(loadBalancingIpRes.getWsPort());
        String key = IM_ROUTE_UIP + uid;

        String value = JSON.toJSONString(connectionInfoRes);
        redisTemplate.opsForValue().set(key, value);

        return connectionInfoRes;
    }

    @Override
    public ConnectionInfoRes connectionInfo(Long uid) {
        String key = IM_ROUTE_UIP + uid;
        String value = redisTemplate.opsForValue().get(key);
        return JSON.parseObject(value, ConnectionInfoRes.class);
    }
}
