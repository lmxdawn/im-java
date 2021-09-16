package com.lmxdawn.user.controller;

import com.lmxdawn.common.res.BaseResponse;
import com.lmxdawn.common.util.ResultVOUtils;
import com.lmxdawn.dubboapi.res.ConnectionInfoRes;
import com.lmxdawn.user.annotation.LoginAuthAnnotation;
import com.lmxdawn.user.service.ImService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Api(tags = "用户")
@RestController
@RequestMapping("/member")
public class MemberController {

    @Resource
    private ImService imService;

    @ApiOperation(value = "获取ws连接信息")
    @PostMapping("/wsConnectionInfo")
    @LoginAuthAnnotation
    public BaseResponse<ConnectionInfoRes> connectionInfo(HttpServletRequest request) {

        Long uid = (Long) request.getAttribute("uid");
        ConnectionInfoRes connectionInfoRes = imService.connectionInfo(uid);

        return ResultVOUtils.success(connectionInfoRes);
    }


}
