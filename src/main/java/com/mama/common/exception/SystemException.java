package com.mama.common.exception;


import com.mama.common.result.basicResult.IResponse;
import lombok.Data;

/**
 * 自定义异常类
 */
@Data
public class SystemException extends RuntimeException{
	private static final long serialVersionUID = -8211729708677743232L;

	private IResponse iResponse;// 编码代码

	public SystemException(IResponse iResponse) {
		this.iResponse=iResponse;
	}

	public SystemException(Throwable cause) {
		if (cause instanceof SystemException) {
			this.iResponse=((SystemException) cause).iResponse;
		}
	}

	public SystemException(IResponse iResponse, Throwable cause) {
		super(cause);
		this.iResponse=iResponse;
	}

}
