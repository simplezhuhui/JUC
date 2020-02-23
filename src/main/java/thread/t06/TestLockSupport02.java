package thread.t06;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * 1.Locksupport不需要synchronized加锁就可以实现线程的阻塞和唤醒
 * 2.unpark()可以先于park()执行，并且线程不会阻塞
 * 3.如果一个线程处于等待状态，连续调用了两次park()方法，就会是该线程无法被唤醒。
 * 实现原理：是由Unsafe类提供的（由C/C++编写），通过一个变量作为标识
 */
public class TestLockSupport02 {
    public static void main(String[] args) {
        Thread t = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                System.out.println(i);
                if (i == 5) {
                    LockSupport.park();
                }
                if(i==8){
                    LockSupport.park();
                }
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();
        LockSupport.unpark(t);//只能唤醒一个阻塞
    }
}
