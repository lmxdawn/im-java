package com.lmxdawn.user.aspect;

import com.lmxdawn.common.enums.ResultEnum;
import com.lmxdawn.common.exception.JsonException;
import com.lmxdawn.common.util.JwtUtils;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 登录验证 AOP
 */
@Aspect
@Component
@Slf4j
public class LoginAuthorizeAspect {

    @Pointcut("@annotation(com.lmxdawn.user.annotation.LoginAuthAnnotation)")
    public void loginVerify() {
    }

    /**
     * 登录验证
     *
     * @param joinPoint
     */
    @Before("loginVerify()")
    public void doLoginAuthVerify(JoinPoint joinPoint) {

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            throw new JsonException(ResultEnum.NOT_NETWORK);
        }
        HttpServletRequest request = attributes.getRequest();
        String token = request.getHeader("x-token");
        if (token == null) {
            throw new JsonException(ResultEnum.LOGIN_VERIFY_FALL);
        }

        // 验证 token
        Claims claims = JwtUtils.parse(token);
        if (claims == null) {
            throw new JsonException(ResultEnum.LOGIN_VERIFY_FALL);
        }
        long uid;
        try {
            uid = Long.parseLong(claims.get("uid").toString());
        }catch (Exception e) {
            throw new JsonException(ResultEnum.LOGIN_VERIFY_FALL);
        }
        if (uid <= 0) {
            throw new JsonException(ResultEnum.LOGIN_VERIFY_FALL);
        }

        // 设置
        request.setAttribute("uid", uid);
    }

}
