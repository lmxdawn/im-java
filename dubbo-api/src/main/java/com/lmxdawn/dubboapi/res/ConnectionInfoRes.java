package com.lmxdawn.dubboapi.res;

import lombok.Data;

import java.io.Serializable;

/**
 * 连接信息
 */
@Data
public class ConnectionInfoRes implements Serializable {
    // ip地址
    private String ip;

    // http端口
    private String httpPort;

    // ws端口
    private String wsPort;
}
