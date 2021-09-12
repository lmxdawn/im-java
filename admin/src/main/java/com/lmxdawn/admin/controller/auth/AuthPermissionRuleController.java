package com.lmxdawn.admin.controller.auth;

import com.lmxdawn.admin.annotation.AuthRuleAnnotation;
import com.lmxdawn.admin.entity.auth.AuthPermissionRule;
import com.lmxdawn.common.enums.ResultEnum;
import com.lmxdawn.admin.req.auth.AuthPermissionRuleSaveRequest;
import com.lmxdawn.admin.service.auth.AuthPermissionRuleService;
import com.lmxdawn.admin.util.PermissionRuleTreeUtils;
import com.lmxdawn.common.res.BaseResponse;
import com.lmxdawn.admin.res.auth.AuthPermissionRuleMergeResponse;
import com.lmxdawn.common.util.ResultVOUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 权限规则相关
 */
@Api(tags = "权限规则管理")
@RestController
public class AuthPermissionRuleController {

    @Resource
    private AuthPermissionRuleService authPermissionRuleService;

    /**
     * 列表
     * @return
     */
    @ApiOperation(value = "权限规则列表")
    @AuthRuleAnnotation("auth/permission_rule/index")
    @GetMapping("/auth/permission_rule/index")
    public BaseResponse<List<AuthPermissionRuleMergeResponse>> index() {


        List<AuthPermissionRule> authPermissionRuleList = authPermissionRuleService.listAll();
        List<AuthPermissionRuleMergeResponse> merge = PermissionRuleTreeUtils.merge(authPermissionRuleList,0L);

        Map<String,Object> restMap = new HashMap<>();
        restMap.put("list", merge);
        return ResultVOUtils.success(restMap);
    }

    /**
     * 新增
     * @param authPermissionRuleSaveRequest
     * @param bindingResult
     * @return
     */
    @ApiOperation(value = "新增权限规则")
    @AuthRuleAnnotation("auth/permission_rule/save")
    @PostMapping("/auth/permission_rule/save")
    public BaseResponse save(@RequestBody @Valid AuthPermissionRuleSaveRequest authPermissionRuleSaveRequest,
                             BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FALL, bindingResult.getFieldError().getDefaultMessage());
        }

        if (authPermissionRuleSaveRequest.getPid() == null) {
            authPermissionRuleSaveRequest.setPid(0L); // 默认设置
        }
        AuthPermissionRule authPermissionRule = new AuthPermissionRule();
        BeanUtils.copyProperties(authPermissionRuleSaveRequest, authPermissionRule);

        boolean b = authPermissionRuleService.insertAuthPermissionRule(authPermissionRule);
        if (!b) {
            return ResultVOUtils.error(ResultEnum.NOT_NETWORK);
        }

        Map<String, Long> res = new HashMap<>();
        res.put("id", authPermissionRule.getId());
        return ResultVOUtils.success(res);
    }

    /**
     * 编辑
     * @param authPermissionRuleSaveRequest
     * @param bindingResult
     * @return
     */
    @ApiOperation(value = "编辑权限规则")
    @AuthRuleAnnotation("auth/permission_rule/edit")
    @PostMapping("/auth/permission_rule/edit")
    public BaseResponse edit(@RequestBody @Valid AuthPermissionRuleSaveRequest authPermissionRuleSaveRequest,
                             BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FALL, bindingResult.getFieldError().getDefaultMessage());
        }

        if (authPermissionRuleSaveRequest.getId() == null) {
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FALL);
        }

        authPermissionRuleSaveRequest.setPid(null); // 不能修改父级 pid

        AuthPermissionRule authPermissionRule = new AuthPermissionRule();
        BeanUtils.copyProperties(authPermissionRuleSaveRequest, authPermissionRule);

        boolean b = authPermissionRuleService.updateAuthPermissionRule(authPermissionRule);
        if (!b) {
            return ResultVOUtils.error(ResultEnum.NOT_NETWORK);
        }

        return ResultVOUtils.success();
    }

    /**
     * 删除
     * @param authPermissionRuleSaveRequest
     * @return
     */
    @ApiOperation(value = "删除权限规则")
    @AuthRuleAnnotation("auth/permission_rule/delete")
    @PostMapping("/auth/permission_rule/delete")
    public BaseResponse delete(@RequestBody AuthPermissionRuleSaveRequest authPermissionRuleSaveRequest) {

        if (authPermissionRuleSaveRequest.getId() == null) {
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FALL);
        }

        boolean b = authPermissionRuleService.deleteById(authPermissionRuleSaveRequest.getId());
        if (!b) {
            return ResultVOUtils.error(ResultEnum.NOT_NETWORK);
        }

        return ResultVOUtils.success();
    }


}
