package com.lmxdawn.admin.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 简单的分页返回对象
 */
@ApiModel
@Data
public class PageSimpleRes<T> {
    // 总数
    @ApiModelProperty(value = "分页总数")
    private Long total;
    // 列表
    @ApiModelProperty(value = "分页列表")
    private List<T> list;
}
