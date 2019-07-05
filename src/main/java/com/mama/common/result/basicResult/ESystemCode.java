/**
 * FileName: ESystemCode
 * Author:   何潮
 * Date:     2018/12/14 14:00
 * Description: 系统统一状态码
 * History:
 * <author>          <time>                <version>          <desc>
 * 何潮           2018/12/14 14:00           1.0             创建版本
 */
package com.mama.common.result.basicResult;

/**
 * 系统统一状态码
 * @author 何潮
 * @create 2018/12/14 14:00
 * @since 1.0.0
 */
public enum ESystemCode implements IResponse {

    // 通用响应码
    SUCCESS(200, "请求处理完成"),
    FAIL(10000, "请求处理失败"),
    PARAM_FAIL(10001, "参数异常"),
    USER_UNLOGIN(10002, "未登录"),
    PARAM_ERROR(10003, "手机号验证码错误"),
    SIGN_ERROR(10004,"签名错误"),
    TIMESTAMP_ERROR(10005,"时间戳不能为空"),
    TIMESTAMP_TIMEOUT(10006,"接口访问超时"),
    REPEAT_REQUEST(10007, "请勿重复提交请求"),

    FAIL_TSET(21000, "测试系统业务错误"),
    ;

    private final int code;
    private final String value;

    ESystemCode(int code, String value) {
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