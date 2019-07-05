package com.mama.service.impl;

import com.mama.common.util.BeanUtil;
import com.mama.model.Test;
import com.mama.dao.TestMapper;
import com.mama.model.vo.TestVo;
import com.mama.service.TestService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author YHY
 * @since 2019-07-05
 */
@Service
public class TestServiceImpl extends ServiceImpl<TestMapper, Test> implements TestService {

    @Autowired
    private TestMapper testMapper;

    @Override
    public void addTest(TestVo testVo) {
        testMapper.insert(BeanUtil.convert(testVo, Test.class));
    }
}
