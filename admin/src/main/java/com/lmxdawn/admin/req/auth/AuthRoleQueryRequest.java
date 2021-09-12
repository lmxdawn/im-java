package com.lmxdawn.admin.req.auth;

import com.lmxdawn.admin.req.ListPageRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 角色的查询表单
 */
@ApiModel
@EqualsAndHashCode(callSuper = true)
@Data
public class AuthRoleQueryRequest extends ListPageRequest {
    @ApiModelProperty(value = "权限名称")
    private String name;
    @ApiModelProperty(value = "状态")
    private Integer status;

}
