/**
 * Copyright (C), 2018-2019
 * FileName: Test
 * Author:   Administrator
 * Date:     2019/7/9 0009 18:44
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 杨浩宇           修改时间           版本号              描述
 */
package com.mama.test;

public class Test1 {

    public static void main(String[] args) {
        Children1 c = new Children1();
    }
}

class Parent1 {
    public static int a = 1;

    public static int b ;

    public Parent1(){
        a++;
        System.out.println(a+"a.init");
    }

    static {
        a++;
        System.out.println(a+"a.static");
    }

    public static void test1(){
        a++;
        System.out.println(a+"a.test1");
    }

}

class Children1 extends Parent1 {
    public static int c = 1;

    public static int d ;

    static {
        c++;
        System.out.println(c+"c.static");
    }

    public Children1(){
        c++;
        System.out.println(c+"c.init");
    }

    public static void test2(){
        c++;
        System.out.println(c+"c.test2");
    }
}
