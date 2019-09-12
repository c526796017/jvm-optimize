/**
 * Copyright (C), 2018-2019
 * FileName: TestController
 * Author:   Administrator
 * Date:     2019/7/5 0005 17:25
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 杨浩宇           修改时间           版本号              描述
 */
package com.mama.controller;

import com.mama.common.result.basicResult.JsonView;
import com.mama.common.result.basicResult.ResponseResult;
import com.mama.model.vo.TestVo;
import com.mama.service.TestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Api(tags = "测试模块")
@RestController
@RequestMapping("test")
public class TestController {

    @Autowired
    private TestService testService;

    @PostMapping("test")
    @ApiOperation("添加测试对象")
    public ResponseResult<Void> addTest(@RequestBody TestVo testVo) {
        testService.addTest(testVo);
        return new JsonView<>().success();
    }

}
