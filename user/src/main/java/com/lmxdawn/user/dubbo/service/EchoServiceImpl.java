package com.lmxdawn.user.dubbo.service;

import com.lmxdawn.dubboapi.service.EchoService;
import org.apache.dubbo.config.annotation.DubboService;

@DubboService
public class EchoServiceImpl implements EchoService {
    @Override
    public String echo(String message) {
        return "Echo " + message;
    }
}
