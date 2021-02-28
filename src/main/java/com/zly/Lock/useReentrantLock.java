package com.zly.Lock;

import java.util.*;
import java.util.concurrent.locks.ReentrantLock;

public class useReentrantLock {
    private final static ReentrantLock reentrantLock = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException{
        final Map<Integer, String> unsafe = new HashMap<Integer, String>();
        final Map<Integer, String> safe = new HashMap<Integer, String>();
        for(int i = 0; i< 2000; i++){
            final int finalI = i;
            new Thread(){
                @Override
                public void run() {
                    safeMethod(safe, finalI);
                }
            }.start();
        }
        for(int i = 0; i< 2000; i++){
            final int finalI = i;
            new Thread(){
                @Override
                public void run() {
                    unsafeMethod(unsafe, finalI);
                }
            }.start();
        }


        Thread.sleep(2000);
        System.out.println("safe: " + safe.size() );
        for(Integer each : unsafe.keySet()){
            safe.remove(each);
        }

        System.out.println("unsafe: "+ unsafe.size());
        System.out.println("lack: " + safe);
        System.out.println("total: " + (safe.size() + unsafe.size()));

    }
    /**
     * 输出结果，线程执行结果看CPU调度，但是基本都是：
     *  safe：2000
     *  unsafe: 1995
     *  lack: (略，但lack.size往往!=5)
     *  total: 2012 (原因， unsafe.size() != 实际上unsafe内元素的个数，
     *  因为HashMap.size(), 返回的是内部属性int size， 这个值的自增不安全)
     */

    public static int unsafeMethod(Map<Integer, String> map, int i){
        map.put(i, String.valueOf(i));
        return i;
    }

    public static int safeMethod(Map<Integer, String> map, int i){
        reentrantLock.lock();
        map.put(i, String.valueOf(i));
        reentrantLock.unlock();
        return i;
    }
}
