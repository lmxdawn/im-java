package com.lmxdawn.user.controller;

import com.lmxdawn.common.enums.ResultEnum;
import com.lmxdawn.common.res.BaseResponse;
import com.lmxdawn.common.util.PasswordUtils;
import com.lmxdawn.common.util.ResultVOUtils;
import com.lmxdawn.user.entity.Member;
import com.lmxdawn.user.req.LoginPwdReq;
import com.lmxdawn.user.res.LoginTokenRes;
import com.lmxdawn.user.service.MemberService;
import com.lmxdawn.user.service.LoginService;
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
public class LoginController {

    @Resource
    private MemberService memberService;

    @Resource
    private LoginService loginService;

    @ApiOperation(value = "密码登录")
    @PostMapping("/byPwd")
    public BaseResponse<LoginTokenRes> byPwd(@RequestBody @Valid LoginPwdReq loginPwdReq,
                                             BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FALL, bindingResult.getFieldError().getDefaultMessage());
        }

        String tel = loginPwdReq.getTel();
        String pwd = loginPwdReq.getPwd();
        Member byTel = memberService.findByTel(tel);
        if (!PasswordUtils.memberPwd(pwd).equals(byTel.getPwd())) {
            return ResultVOUtils.error(ResultEnum.USER_LOGIN_PWD_ERR);
        }

        String token = loginService.createToken(byTel.getUid());

        LoginTokenRes loginTokenRes = new LoginTokenRes();
        loginTokenRes.setUid(byTel.getUid());
        loginTokenRes.setToken(token);

        return ResultVOUtils.success(loginTokenRes);
    }


}
