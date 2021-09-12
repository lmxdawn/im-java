package com.lmxdawn.imroute.service;

import com.lmxdawn.imroute.res.LoadBalancingIpRes;


public interface LoadBalancingService {

    LoadBalancingIpRes imIpHash(Long uid);

}
