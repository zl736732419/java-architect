package com.zheng.jvm.heap;

/**
 * @Author zhenglian
 * @Date 2019/3/14
 */
public class Line {
    private Point from;
    private Point to;
    
    public Line(Point from, Point to) {
        this.from = from;
        this.to = to;
    }

    public Point getFrom() {
        return from;
    }

    public void setFrom(Point from) {
        this.from = from;
    }

    public Point getTo() {
        return to;
    }

    public void setTo(Point to) {
        this.to = to;
    }
}
