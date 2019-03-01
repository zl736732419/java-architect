package com.zheng.highconcurrent.queue;

import org.junit.Test;

import java.util.stream.IntStream;

/**
 * @Author zhenglian
 * @Date 2019/3/1
 */
public class PriorityQueueTest {
    private Integer[] queue = new Integer[] {1,5,10,3,4,6,2,9,7,8};
    private int size = 10;
    @Test
    public void sort() {
        heapify(size);
        IntStream.range(0, queue.length)
                .forEach(i -> System.out.println(queue[i]));
    }

    private void heapify(int size) {
        for (int i = (size >>> 1) - 1; i >= 0; i--)
            siftDownUsingComparator(i, queue[i]);
    }

    private void siftDownUsingComparator(int k, Integer x) {
        int half = size >>> 1;
        while (k < half) {
            int child = (k << 1) + 1;
            Integer c = queue[child];
            int right = child + 1;
            if (right < size && c > queue[right])
                c = queue[child = right];
            if (x <= c)
                break;
            queue[k] = c;
            k = child;
        }
        queue[k] = x;
    }
}
