package com.mama.common.util;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;


/**
 * 计算并打印耗时工具类
 *
 * @author zyq
 * @create 2018/12/21 14:19
 * @since 1.0.0
 */
@Data
@Slf4j
public class ExecTimeUtil {

    private String name;

    /**
     * 开始时间
     */
    private Long startTime;

    /**
     * 上次打印时间
     */
    private Long lastTime;

    /**
     * 构造并开始计时
     */
    public ExecTimeUtil(String name){
        this.name = name;
        //reStart();
    }

    /**
     * 重新开始计时
     */
    public void reStart(){

        this.startTime = System.currentTimeMillis();

        this.lastTime = this.startTime;
    }

    /**
     * 重新开始计时
     */
    public void end(){

        this.startTime = null;

        this.lastTime = null;
    }

    /**
     * 打印耗时
     * @param tag 耗时统计名称
     */
    public void logTime(String tag){

        long curr = System.currentTimeMillis();

        long fromLastCost = 0;

        if(this.lastTime == null) {
            lastTime=System.currentTimeMillis();
        }
        if(this.startTime==null){
            startTime=System.currentTimeMillis();
        }
        fromLastCost = curr - lastTime;

        long cost = curr - startTime;
        log.info("cost time:{} {} last:{}ms all:{}ms",new Object[]{this.name,tag,fromLastCost,cost});
        
        this.lastTime = curr;

    }
    /**
     * 打印耗时
     * @param tag 耗时统计名称
     * @param reStart 是否重置开始时间
     */
    public void logTime(String tag, boolean reStart){

        this.logTime(tag);

        if(reStart){

            reStart();

        }

    }


    public static void main(String[] args) {
        try {
            ExecTimeUtil et = new ExecTimeUtil("测试");
            Thread.sleep(1000);
            et.logTime("test1");
            Thread.sleep(1000);
            et.logTime("test2");
            Thread.sleep(1000);
            et.logTime("test2");
        }catch (Exception ex){
        }
    }

}
