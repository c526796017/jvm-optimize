package com.mama.common.util;


import com.mama.common.constant.Constant;

/**
 * Copyright (C), 2018-2019, 康之家有限公司
 * FileName: UUIDUtil
 * Author:   Administrator
 * Date:     2019/1/31/031 18:02
 * Description: ${DESCRIPTION}
 * History:
 * <author>          <time>          <version>          <desc>
 * 钟永勤           修改时间           版本号              描述
 */
public class UUIDUtil {
    private static  SnowflakeUtil snowflakeUtil;
    static {
        snowflakeUtil=new SnowflakeUtil(Constant.UUID_WORKERID,Constant.UUID_DATACENTERID);
    }

    /**
     * 分布式uuid产生方法 15位id；
     * @return
     */
    public static Long getUuid(){
        return Long.valueOf(String.valueOf(snowflakeUtil.nextId()).substring(3));
    }

    /**
     * j分布式uuid13位产生方法
     */
    public static Long getUuid13(){
        return Long.valueOf(String.valueOf(snowflakeUtil.nextId()).substring(5));
    }

    /**
     * 分布式uuid10位产生方法
     */
    public static Long getUuid10(){
        return Long.valueOf(String.valueOf(snowflakeUtil.nextId()).substring(8));
    }


}
