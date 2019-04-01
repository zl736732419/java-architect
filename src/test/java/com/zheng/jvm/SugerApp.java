package com.zheng.jvm;

import org.junit.Test;

/**
 * @Author zhenglian
 * @Date 2019/4/1
 */
public class SugerApp {
    
    @Test
    public void test() {
        Integer a = 1;
        Integer b = 2;
        Integer c = 3;
        Integer d = 3;
        Integer e = 321;
        Integer f = 321;
        Long g = 3L;
        System.out.println(c == d); // true
        System.out.println(e == f); // false  (-128,128)会被放入常量池中
        System.out.println(c == (a+b)); // true
        System.out.println(c.equals(a+b)); // true
        System.out.println(g == (a+b)); // true ==在遇到运算符会进行自动转型
        System.out.println(g.equals(a+b)); // false equals不处理数据类型转换
    }
}
