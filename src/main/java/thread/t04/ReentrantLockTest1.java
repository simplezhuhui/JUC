package thread.t04;

import java.util.concurrent.TimeUnit;

/**
 * synchronized是可重入的锁，同一个线程在执行m1()过程中，调用m2()方法，发现申请的是同一把锁this,可重入，是可以调用的。
 */
public class ReentrantLockTest1 {
    synchronized void m1(){
        for (int i = 0; i <10 ; i++) {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(i);
            if(i==2) m2();
        }
    }

    synchronized void m2() {
        System.out.println("m2...");
    }

    public static void main(String[] args) {
        ReentrantLockTest1 r = new ReentrantLockTest1();
        new Thread(r::m1).start();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
