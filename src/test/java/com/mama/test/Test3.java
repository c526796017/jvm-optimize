/**
 * Copyright (C), 2018-2019, 康之家有限公司
 * FileName: Test3
 * Author:   Administrator
 * Date:     2019/7/15 0015 19:40
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 杨浩宇           修改时间           版本号              描述
 */
package com.mama.test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;

public class Test3 extends ClassLoader {

    private String className;

    private static final String exName = ".class";


    public Test3(String className) {
        super();
        this.className = className;
    }

    public Test3(ClassLoader classLoader, String className) {
        super(classLoader);
        this.className = className;
    }

    @Override
    protected Class<?> findClass(String name) {
        byte[] data = loadClassData(name);
        return defineClass(name, data, 0, data.length);
    }

    public byte[] loadClassData(String className) {
        byte[] data = new byte[1024];
        try (FileInputStream fis = new FileInputStream(new File(className + exName));
             ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            int ch = 0;
            while (-1 != (fis.read())) {
                bos.write(ch);
            }
            data = bos.toByteArray();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    public static void test(ClassLoader classLoader) throws Exception {
        Class<?> clazz = classLoader.loadClass("com.mama.test.Test1");
        System.out.println(clazz.getClassLoader());
        Object o = clazz.newInstance();
        System.out.println(o);
    }

    public static void main(String[] args) throws Exception {
        Test3 test3 = new Test3("test3ClassLoad");
        System.out.println(test3.getClass().getClassLoader());
        Test3.test(test3);
    }

}
