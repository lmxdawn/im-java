package com.lmxdawn.user.controller;

import com.lmxdawn.common.enums.ResultEnum;
import com.lmxdawn.common.res.BaseResponse;
import com.lmxdawn.common.util.PasswordUtils;
import com.lmxdawn.common.util.ResultVOUtils;
import com.lmxdawn.user.entity.Member;
import com.lmxdawn.user.req.UserLoginPwdReq;
import com.lmxdawn.user.res.UserLoginTokenRes;
import com.lmxdawn.user.service.MemberService;
import com.lmxdawn.user.util.LoginUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

@Api(tags = "用户登录")
@RestController
@RequestMapping("/login")
public class UserLoginController {

    @Resource
    private MemberService memberService;

    @ApiOperation(value = "密码登录")
    @PostMapping("/byPwd")
    public BaseResponse<UserLoginTokenRes> byPwd(@RequestBody @Valid UserLoginPwdReq userLoginPwdReq,
                                                 BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FALL, bindingResult.getFieldError().getDefaultMessage());
        }

        String tel = userLoginPwdReq.getTel();
        String pwd = userLoginPwdReq.getPwd();
        Member byTel = memberService.findByTel(tel);
        if (!PasswordUtils.memberPwd(pwd).equals(byTel.getPwd())) {
            return ResultVOUtils.error(ResultEnum.USER_LOGIN_PWD_ERR);
        }

        String token = LoginUtils.createToken(byTel.getUid());

        UserLoginTokenRes userLoginTokenRes = new UserLoginTokenRes();
        userLoginTokenRes.setUid(byTel.getUid());
        userLoginTokenRes.setToken(token);

        return ResultVOUtils.success(userLoginTokenRes);
    }


}
