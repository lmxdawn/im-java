package com.lmxdawn.user.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class UserLoginTokenRes {

    @ApiModelProperty(value = "用户ID")
    private Long uid;

    @ApiModelProperty(value = "用户token")
    private String token;

}
