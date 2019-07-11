/**
 * Copyright (C), 2018-2019, 康之家有限公司
 * FileName: Test2
 * Author:   Administrator
 * Date:     2019/7/11 0011 11:44
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 杨浩宇           修改时间           版本号              描述
 */
package com.mama.test;

public class Test2 {
    public static void main(String[] args) {
        System.out.println(Children2.getB());
        System.out.println("---------------------------------");
        Children2 c = new Children2();
    }

}

class Parent2{
    public static int a = 4;

    static {
        a++;
        System.out.println(a+"a1");
    }

    {
        a++;
        System.out.println(a+"a2");
    }

    public Parent2(){
        a++;
        System.out.println(a+"a3");
    }

    public static int getA(){
        a =1;
        System.out.println(a+"a4");
        return a;
    }

    public int getD(){
        a=2;
        System.out.println(a+"a5");
        return a;
    }
}

class Children2 extends Parent2{
    public static int b = 4;

    static {
        b++;
        System.out.println(b+"b1");
    }

    {
        b++;
        System.out.println(b+"b2");
    }

    public Children2(){
        b++;
        System.out.println(b+"b3");
    }

    public static int getB(){
        b =1;
        System.out.println(b+"b4");
        return b;
    }

    public int getC(){
        b=2;
        System.out.println(b+"b5");
        return b;
    }
}