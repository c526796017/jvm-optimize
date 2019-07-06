/**
 * Copyright (C), 2018-2019, 康之家有限公司
 * FileName: TestVo
 * Author:   Administrator
 * Date:     2019/7/5 0005 17:35
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 杨浩宇           修改时间           版本号              描述
 */
package com.mama.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TestVo {
    @ApiModelProperty("测试对象id")
    private Long id;
    @ApiModelProperty("测试对象名称")
    private String name;
}
