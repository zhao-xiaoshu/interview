package com.atguigu.juc;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author zts
 * @create 2020-03-15 20:12
 */

/**
 * 原子性是什么意思？    不保证原子性：可能出现写覆盖,当一个线程写入，可能另一个线程被挂起，当被唤醒时没有拿到最新值，继续写它原来的值
 * 如何解决:    加synchronized，杀鸡用牛刀
 * 不可分割，即某个线程正在做具体业务时，在中间不可以被加塞或者分割，需要整体完整
 */
class MyData {
    volatile int number = 0; //volatile保证可见性，当线程1(拷贝到本地内存，再写回主内存)修改值，会立刻通知线程2

    public void addTo60() {
        this.number = 60;
    }

    public  void addPlusPlus() {
        number++;
    }

    AtomicInteger atomicInteger = new AtomicInteger();
    public void addAomic(){
        atomicInteger.getAndIncrement();
    }
}

public class VolatileDemo {
    public static void main(String[] args) {
        MyData myData = new MyData();
        for (int i = 1; i <= 20; i++) {
            new Thread(() -> {
                for (int j = 1; j <= 1000; j++) {
                    myData.addPlusPlus();
                    myData.addAomic();
                }
            }, String.valueOf(i)).start();
        }
        //等上面20个线程执行完，在用main线程取得最终结果值
        while (Thread.activeCount() > 2) {
            Thread.yield();
        }
        System.out.println(Thread.currentThread().getName() + "\t finally number:" + myData.number);
        System.out.println(Thread.currentThread().getName() + "\t finally atomicInteger:" + myData.atomicInteger);
    }

    //volatile可以保证可见性，及时通知其他线程，主物理内存的值已经被修改
    private static void seeOkByVolatile() {
        MyData myData = new MyData();
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName());
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            myData.addTo60();
            System.out.println(Thread.currentThread().getName() + "update:" + myData.number);
        }, "AA").start();

        while (myData.number == 0) {

        }
        System.out.println(Thread.currentThread().getName() + "main thread" + myData.number);
    }
}
