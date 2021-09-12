package com.lmxdawn.user.controller;

import com.lmxdawn.common.res.BaseResponse;
import com.lmxdawn.common.util.ResultVOUtils;
import com.lmxdawn.user.res.EchoResponse;
import io.swagger.annotations.*;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Api(tags = "测试接口")
@RestController
public class EchoController {

    // @DubboReference
    // private EchoService echoService;

    @Autowired
    private DiscoveryClient discoveryClient;

    @ApiOperation(value = "打印信息", notes = "打印信息出来")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "str", value = "需要打印的字符", dataType = "string", paramType = "path")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "success", response = EchoResponse.class),
            @ApiResponse(code = 401, message = "xxx具体错误")
    })
    @GetMapping("/echo/{str}")
    public BaseResponse<EchoResponse> echo(@PathVariable String str) {
        EchoResponse echoResponse = new EchoResponse();
        echoResponse.setStr(str);
        List<ServiceInstance> instances = discoveryClient.getInstances("service-im");
        for (ServiceInstance i : instances) {
            System.out.println(i.getHost() + i.getPort());
            Map<String, String> metadata = i.getMetadata();
            for (String ii : metadata.keySet()) {
                if (ii.equals("server-port") || ii.equals("ws-port")) {
                    System.out.println(ii + ": " + metadata.get(ii));
                }
            }
        }
        return ResultVOUtils.success(echoResponse);
    }

}
