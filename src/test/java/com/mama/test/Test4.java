/**
 * Copyright (C), 2018-2019, 康之家有限公司
 * FileName: Test4
 * Author:   admin
 * Date:     2019/9/12 10:34
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 杨浩宇           修改时间           版本号              描述
 */
package com.mama.test;

import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;

import java.util.ArrayList;

public class Test4 {
    public static void main(String[] args) {
        String a = "";
        String b = "c";
        Joiner joiner = Joiner.on(",").skipNulls();
        System.out.println(joiner.join(Lists.newArrayList(a, null, b)));
    }
}
