package com.lmxdawn.imroute.res;

import lombok.Data;

@Data
public class LoadBalancingIpRes {

    // ip地址
    private String ip;

    // http端口
    private String httpPort;

    // ws端口
    private String wsPort;

}
