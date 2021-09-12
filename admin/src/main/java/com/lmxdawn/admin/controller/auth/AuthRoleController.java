package com.lmxdawn.admin.controller.auth;

import com.github.pagehelper.PageInfo;
import com.lmxdawn.admin.annotation.AuthRuleAnnotation;
import com.lmxdawn.admin.entity.auth.AuthPermission;
import com.lmxdawn.admin.entity.auth.AuthPermissionRule;
import com.lmxdawn.admin.entity.auth.AuthRole;
import com.lmxdawn.common.enums.ResultEnum;
import com.lmxdawn.admin.req.auth.AuthRoleAuthRequest;
import com.lmxdawn.admin.req.auth.AuthRoleQueryRequest;
import com.lmxdawn.admin.req.auth.AuthRoleSaveRequest;
import com.lmxdawn.admin.service.auth.AuthPermissionRuleService;
import com.lmxdawn.admin.service.auth.AuthPermissionService;
import com.lmxdawn.admin.service.auth.AuthRoleService;
import com.lmxdawn.admin.util.PermissionRuleTreeUtils;
import com.lmxdawn.admin.res.PageSimpleResponse;
import com.lmxdawn.common.res.BaseResponse;
import com.lmxdawn.admin.res.auth.AuthPermissionRuleMergeResponse;
import com.lmxdawn.admin.res.auth.AuthRoleResponse;
import com.lmxdawn.common.util.ResultVOUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 角色相关
 */
@Api(tags = "角色管理")
@RestController
public class AuthRoleController {

    @Resource
    private AuthRoleService authRoleService;

    @Resource
    private AuthPermissionRuleService authPermissionRuleService;

    @Resource
    private AuthPermissionService authPermissionService;

    /**
     * 角色列表
     */
    @ApiOperation(value = "角色列表")
    @AuthRuleAnnotation("auth/role/index")
    @GetMapping("/auth/role/index")
    public BaseResponse<PageSimpleResponse<AuthRoleResponse>> index(@Valid AuthRoleQueryRequest authRoleQueryRequest,
                              BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FALL, bindingResult.getFieldError().getDefaultMessage());
        }

        List<AuthRole> authRoleList = authRoleService.listAdminPage(authRoleQueryRequest);
        List<AuthRoleResponse> authRoleResponseList = authRoleList.stream().map(item -> {
            AuthRoleResponse authRoleResponse = new AuthRoleResponse();
            BeanUtils.copyProperties(item, authRoleResponse);
            return authRoleResponse;
        }).collect(Collectors.toList());

        PageInfo<AuthRole> pageInfo = new PageInfo<>(authRoleList);
        PageSimpleResponse<AuthRoleResponse> pageSimpleResponse = new PageSimpleResponse<>();
        pageSimpleResponse.setTotal(pageInfo.getTotal());
        pageSimpleResponse.setList(authRoleResponseList);
        return ResultVOUtils.success(pageSimpleResponse);
    }

    /**
     * 获取授权列表
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "获取已授权列表")
    @AuthRuleAnnotation("auth/role/authList")
    @ApiImplicitParam(name = "id", value = "角色ID")
    @GetMapping("/auth/role/authList")
    public BaseResponse authList(@RequestParam("id") Long id) {

        // 查询当前角色拥有的权限id
        List<AuthPermission> authPermissionList = authPermissionService.listByRoleId(id);
        List<Long> checkedKeys = authPermissionList.stream()
                .map(AuthPermission::getPermissionRuleId)
                .collect(Collectors.toList());

        // 查询所有权限规则
        List<AuthPermissionRule> authPermissionRuleList = authPermissionRuleService.listAll();
        List<AuthPermissionRuleMergeResponse> merge = PermissionRuleTreeUtils.merge(authPermissionRuleList, 0L);

        Map<String, Object> restMap = new HashMap<>();
        restMap.put("list", merge);
        restMap.put("checkedKeys", checkedKeys);
        return ResultVOUtils.success(restMap);
    }

    /**
     * 授权
     * @param authRoleAuthRequest
     * @param bindingResult
     * @return
     */
    @ApiOperation(value = "授权")
    @AuthRuleAnnotation("auth/role/auth")
    @PostMapping("/auth/role/auth")
    public BaseResponse auth(@RequestBody @Valid AuthRoleAuthRequest authRoleAuthRequest,
                             BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FALL, bindingResult.getFieldError().getDefaultMessage());
        }

        // 先删除之前的授权
        authPermissionService.deleteByRoleId(authRoleAuthRequest.getRoleId());

        List<AuthPermission> authPermissionList = authRoleAuthRequest.getAuthRules().stream()
                .map(aLong -> {
                    AuthPermission authPermission = new AuthPermission();
                    authPermission.setRoleId(authRoleAuthRequest.getRoleId());
                    authPermission.setPermissionRuleId(aLong);
                    authPermission.setType("admin");
                    return authPermission;
                }).collect(Collectors.toList());

        int i = authPermissionService.insertAuthPermissionAll(authPermissionList);

        return ResultVOUtils.success();
    }

    /**
     * 新增
     *
     * @param authRoleSaveRequest
     * @param bindingResult
     * @return
     */
    @ApiOperation(value = "新增角色")
    @AuthRuleAnnotation("auth/role/save")
    @PostMapping("/auth/role/save")
    public BaseResponse save(@RequestBody @Valid AuthRoleSaveRequest authRoleSaveRequest,
                             BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FALL, bindingResult.getFieldError().getDefaultMessage());
        }

        AuthRole byName = authRoleService.findByName(authRoleSaveRequest.getName());
        if (byName != null) {
            return ResultVOUtils.error(ResultEnum.DATA_REPEAT, "当前角色已存在");
        }

        AuthRole authRole = new AuthRole();
        BeanUtils.copyProperties(authRoleSaveRequest, authRole);

        boolean b = authRoleService.insertAuthRole(authRole);
        if (!b) {
            return ResultVOUtils.error(ResultEnum.NOT_NETWORK);
        }

        Map<String, Long> res = new HashMap<>();
        res.put("id", authRole.getId());
        return ResultVOUtils.success(res);
    }

    /**
     * 编辑
     *
     * @param authRoleSaveRequest
     * @param bindingResult
     * @return
     */
    @ApiOperation(value = "编辑角色")
    @AuthRuleAnnotation("auth/role/edit")
    @PostMapping("/auth/role/edit")
    public BaseResponse edit(@RequestBody @Valid AuthRoleSaveRequest authRoleSaveRequest,
                             BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FALL, bindingResult.getFieldError().getDefaultMessage());
        }

        if (authRoleSaveRequest.getId() == null) {
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FALL);
        }

        // 检查是否存在当前角色
        AuthRole byName = authRoleService.findByName(authRoleSaveRequest.getName());
        if (byName != null && !authRoleSaveRequest.getId().equals(byName.getId())) {
            return ResultVOUtils.error(ResultEnum.DATA_REPEAT, "当前角色已存在");
        }

        AuthRole authRole = new AuthRole();
        BeanUtils.copyProperties(authRoleSaveRequest, authRole);

        boolean b = authRoleService.updateAuthRole(authRole);
        if (!b) {
            return ResultVOUtils.error(ResultEnum.NOT_NETWORK);
        }

        return ResultVOUtils.success();
    }

    /**
     * 删除
     *
     * @param authRoleSaveRequest
     * @return
     */
    @ApiOperation(value = "删除角色")
    @AuthRuleAnnotation("auth/role/delete")
    @PostMapping("/auth/role/delete")
    public BaseResponse delete(@RequestBody AuthRoleSaveRequest authRoleSaveRequest) {

        if (authRoleSaveRequest.getId() == null) {
            return ResultVOUtils.error(ResultEnum.PARAM_VERIFY_FALL);
        }

        boolean b = authRoleService.deleteById(authRoleSaveRequest.getId());
        if (!b) {
            return ResultVOUtils.error(ResultEnum.NOT_NETWORK);
        }

        //TODO 删除角色后先前授权的缓存不会消失

        // 再删除之前的授权
        authPermissionService.deleteByRoleId(authRoleSaveRequest.getId());

        return ResultVOUtils.success();
    }


}
