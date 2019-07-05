/**
 * FileName: IResponse
 * Author:   何潮
 * Date:     2018/12/14 13:56
 * Description: 返回状态码接口
 * History:
 * <author>          <time>                <version>          <desc>
 * 何潮           2018/12/14 13:56           1.0             创建版本
 */
package com.mama.common.result.basicResult;

/**
 * 返回状态码接口
 * @author 何潮
 * @create 2018/12/14 13:56
 * @since 1.0.0
 */
public interface IResponse {

    int toCode();

    String toValue();

}