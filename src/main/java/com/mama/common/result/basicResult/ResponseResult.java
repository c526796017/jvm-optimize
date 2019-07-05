/**
 * FileName: ResponseResult
 * Author:   何潮
 * Date:     2018/12/14 14:06
 * Description: 返回响应状态码和结果
 * History:
 * <author>          <time>                <version>          <desc>
 * 何潮           2018/12/14 14:06           1.0             创建版本
 */
package com.mama.common.result.basicResult;


import lombok.Data;

/**
 * 返回响应状态码和结果
 *
 * @author 何潮
 * @create 2018/12/14 14:06
 * @since 1.0.0
 */

@Data
public class ResponseResult<T> {

    // 状态码
    private int code;
    // 数据内容
    private T data;
    // 消息内容
    private String message;

    //调试用错误信息返回
    private T detailMessage;

    //默认返回处理成功
    public ResponseResult() {
        this.code = ESystemCode.SUCCESS.toCode();
        this.message = ESystemCode.SUCCESS.toValue();
    }

    //默认返回处理成功
    public ResponseResult(T data) {
        this.code = ESystemCode.SUCCESS.toCode();
        this.message = ESystemCode.SUCCESS.toValue();
        this.data = data;
    }

    //返回模块状态码
    public ResponseResult(IResponse response) {
        this.code = response.toCode();
        this.message = response.toValue();
    }

    //返回模块状态码
    public ResponseResult(IResponse response, T data) {
        this.code = response.toCode();
        this.message = response.toValue();
        this.data = data;
    }

}