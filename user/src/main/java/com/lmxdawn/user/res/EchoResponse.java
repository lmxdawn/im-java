package com.lmxdawn.user.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "打印信息类")
@Data
public class EchoResponse {
    @ApiModelProperty(value = "具体信息", required = true)
    private String str;
}
