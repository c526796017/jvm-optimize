package com.mama.service;

import com.mama.model.Test;
import com.baomidou.mybatisplus.service.IService;
import com.mama.model.vo.TestVo;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author YHY
 * @since 2019-07-05
 */
public interface TestService extends IService<Test> {

    /**
     * 添加测试对象
     *
     * @param testVo
     */
    void addTest(TestVo testVo);
}
