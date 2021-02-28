package com.zly.synchornized;

public class useSynchronized {


}
class UnsafeZly{

    private static int i = 0;



    // 静态方法处
    public synchronized static void  staticMethod(){

    }

    //类普通方法处
    public synchronized void normalMethod(){

    }

    public void methodA(){
        //代码块
        synchronized (this){
            i ++;
        }
    }

    public void methodB(){
        //持有类锁，此时的锁和静态方法的锁一致
        synchronized (UnsafeZly.class){
            i ++;
        }
    }
}

