package com.lmxdawn.user.controller;

import com.lmxdawn.common.enums.ResultEnum;
import com.lmxdawn.common.res.BaseResponse;
import com.lmxdawn.common.util.ResultVOUtils;
import com.lmxdawn.imcommon.constant.WSResTypeConstant;
import com.lmxdawn.imcommon.req.ImMsgReq;
import com.lmxdawn.user.annotation.LoginAuthAnnotation;
import com.lmxdawn.user.entity.Member;
import com.lmxdawn.user.req.UserMsgReq;
import com.lmxdawn.user.service.ImService;
import com.lmxdawn.user.service.MemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Api(tags = "用户好友")
@RestController
@RequestMapping("/user-friend")
public class UserFriendController {

    @Resource
    private MemberService memberService;

    @Resource
    private ImService imService;

    @ApiOperation(value = "发送好友消息")
    @PostMapping("/msg")
    @LoginAuthAnnotation
    public BaseResponse msg(@RequestBody @Valid UserMsgReq userMsgReq,
                            BindingResult bindingResult,
                            HttpServletRequest request) {

        if (bindingResult.hasErrors()) {
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FALL, bindingResult.getFieldError().getDefaultMessage());
        }

        Long uid = (Long) request.getAttribute("uid");
        Member byUidMember = memberService.findByUid(uid);

        // 查询是否是朋友关系

        ImMsgReq imMsgReq = new ImMsgReq();
        imMsgReq.setType(WSResTypeConstant.FRIEND);
        imMsgReq.setGid(0L);
        imMsgReq.setReceiveId(userMsgReq.getReceiveId());
        imMsgReq.setMsgType(userMsgReq.getMsgType());
        imMsgReq.setMsgContent(userMsgReq.getMsgContent());
        imMsgReq.setUid(uid);
        imMsgReq.setName(byUidMember.getName());
        imMsgReq.setAvatar(byUidMember.getAvatar());
        imMsgReq.setRemark(byUidMember.getRemark());
        imService.pushMsg(userMsgReq.getReceiveId(), imMsgReq);

        return ResultVOUtils.success();
    }

}
