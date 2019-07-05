package com.mama.common.result.basicResult;


import com.mama.common.exception.SystemException;
import lombok.extern.slf4j.Slf4j;


/**
 * Created by yongqin.zhong on 2018-12-21.
 */
@Slf4j
public class JsonView<T> {


    /**
     * 成功返回对象
     *
     * @param data
     * @return
     */
    public ResponseResult success(T data) {
        ResponseResult baseResDTO = new ResponseResult();
        baseResDTO.setData(data);
        return baseResDTO;
    }

    public ResponseResult success() {
        ResponseResult baseResDTO = new ResponseResult();
        return baseResDTO;
    }

    /**
     * 失败返回对象
     *
     * @param ex
     * @param data
     * @return
     */
    public ResponseResult fail(Exception ex, T data) {
        ResponseResult baseResDTO = getKZJException(ex);
        baseResDTO.setData(data);
        return baseResDTO;
    }

    public ResponseResult fail(IResponse iResponse,T data) {
        ResponseResult responseResult = new ResponseResult();
        responseResult.setData(data);
        responseResult.setCode(iResponse.toCode());
        responseResult.setMessage(iResponse.toValue());
        return responseResult;
    }

    public ResponseResult fail(IResponse iResponse) {
        ResponseResult responseResult = new ResponseResult();
        responseResult.setCode(iResponse.toCode());
        responseResult.setMessage(iResponse.toValue());
        return responseResult;
    }

    public ResponseResult fail(T Data) {
        ResponseResult responseResult = new ResponseResult();
        responseResult.setCode(ESystemCode.HEALTHCARE_FAIL.toCode());
        responseResult.setMessage(Data.toString());
        return responseResult;
    }

    public ResponseResult paramFail(T data) {
        ResponseResult responseResult = new ResponseResult();
        responseResult.setCode(ESystemCode.PARAM_FAIL.toCode());
        responseResult.setMessage(data.toString());
        return responseResult;
    }

    /**
     * 失败返回对象
     *
     * @param ex
     * @return
     */
    public static ResponseResult fail(Exception ex) {
        ResponseResult baseResDTO = getKZJException(ex);
        return baseResDTO;
    }

    /**
     * 读取配置，系统是否调试状态
     *
     * @return
     */
    private static boolean isSystemDebug() {
        try {
            //String systemDebug = SystemProperties.getProperties().getProperty("system.debug");
            //if(StringUtils.COMMON_VALUE_YES.equals(systemDebug)){
            return true;
            //}
        } catch (Exception e) {
            log.error("读取是否系统调试配置异常:" + e.getMessage());
        }
        return false;
    }

    /**
     * 将非预知异常转换成已知异常
     *
     * @param ex
     * @return
     */
    private static ResponseResult getKZJException(Exception ex) {

        SystemException zde = null;
        boolean isKzjExc=false;
        if (ex instanceof SystemException) {
            zde = (SystemException) ex;
            isKzjExc=true;
        } else {
            zde = new SystemException(ESystemCode.FAIL, ex);
        }
        ResponseResult baseResDTO = new ResponseResult(zde.getIResponse());
        log.error("ERROR_CODE={},ERROR_NAME={}", baseResDTO.getCode(), baseResDTO.getMessage());
        if (ex.getStackTrace() != null && ex.getStackTrace().length > 0) {
            if(!isKzjExc) {
                log.error("ERROR INFO:", ex);
            }
            baseResDTO.setDetailMessage("cause=" + ex.getCause() + "&StackTrace=" + ex.getStackTrace()[0]);
        } else {
            baseResDTO.setDetailMessage(ex);
        }
        return baseResDTO;
    }
}
