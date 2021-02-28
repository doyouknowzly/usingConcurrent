package com.zly.atomic;

import java.util.concurrent.atomic.AtomicInteger;

public class useAtomic {

    private static int normalCount = 0;
    private final static AtomicInteger atomicCount = new AtomicInteger();

    public static void main(String[] args) throws InterruptedException {
        for(int i=0; i< 20000; i++){
            new Thread(){
                @Override
                public void run() {
                    normalCount = ++normalCount;
                    atomicCount.getAndIncrement();
                }
            }.start();
        }
        Thread.sleep(500);
        System.out.println("normal: " + normalCount);
        System.out.println("atomic: " + atomicCount);
    }
    /**
     * 输出结果，线程执行结果看CPU调度，但是基本都是：
     *  normal：19997
     *  atomic: 20000
     */
}
