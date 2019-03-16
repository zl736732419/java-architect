package com.zheng.jvm.heap;

/**
 * @Author zhenglian
 * @Date 2019/3/14
 */
public class LineApp {
    
    public static void main(String[] args) throws Exception {
        Point a = new Point(0, 0);
        Point b = new Point(1, 1);
        Point c = new Point(2, 2);
        Point d = new Point(3, 3);
        Point e = new Point(4, 4);
        Point f = new Point(5, 5);
        Point g = new Point(6, 6);
        
        Line aLine = new Line(a, b);
        Line bLine = new Line(a, c);
        Line cLine = new Line(d, e);
        Line dLine = new Line(f, g);
        
        a = null;
        b = null;
        c = null;
        d = null;
        e = null;
        
        Thread.sleep(100000);
    }
}
