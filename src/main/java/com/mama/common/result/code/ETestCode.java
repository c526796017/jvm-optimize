/**
 * Copyright (C), 2018
 * FileName: ESystemCode
 * Author:   杨浩宇
 * Date:     2018/12/14 14:00
 * Description: 系统统一状态码
 * History:
 * <author>          <time>                <version>          <desc>
 * 杨浩宇           2018/12/14 14:00           1.0             创建版本
 */
package com.mama.common.result.code;


import com.mama.common.result.basicResult.ESystemCode;
import com.mama.common.result.basicResult.IResponse;

/**
 * 商品状态码
 *
 * @create 2018/12/14 14:00
 * @since 1.0.0
 */
public enum ETestCode implements IResponse {

    // 药品响应码
    ADD_TEST_ERROR(ESystemCode.FAIL_TSET.toCode() + 1, "测试系统添加测试对象错误"),
    ;

    private final int code;
    private final String value;

    ETestCode(int code, String value) {
        this.code = code;
        this.value = value;
    }

    @Override
    public String toValue() {
        return value;
    }

    @Override
    public int toCode() {
        return code;
    }

}