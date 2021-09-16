package com.lmxdawn.admin.req.auth;

import com.lmxdawn.admin.req.ListPageReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@ApiModel
@EqualsAndHashCode(callSuper = true)
@Data
public class AuthAdminQueryReq extends ListPageReq {

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "状态（）")
    private Integer status;

    @ApiModelProperty(value = "角色ID列表")
    private Long roleId;

    @ApiModelProperty(value = "用户ID列表")
    private List<Long> ids;

}
