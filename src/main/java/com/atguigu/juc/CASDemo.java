package com.atguigu.juc;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * CAS比较并交换
 * @author zts
 * @create 2020-03-16 22:07
 */
public class CASDemo {
    public static void main(String[] args) {
        AtomicInteger atomicInteger = new AtomicInteger(5);
        System.out.println(atomicInteger.compareAndSet(5, 2019) + "\tcurrent data:" + atomicInteger.get());
        System.out.println(atomicInteger.compareAndSet(5, 2014) + "\tcurrent data:" + atomicInteger.get());
        atomicInteger.getAndIncrement();
        System.out.println(atomicInteger.compareAndSet(5, 2014) + "\tcurrent data:" + atomicInteger.get());
    }
}
