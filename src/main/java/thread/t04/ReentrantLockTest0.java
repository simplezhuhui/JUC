package thread.t04;

import java.util.concurrent.TimeUnit;

/**
 *线程进入同步代码块后，当前时间片用完之后，但还没有执行完代码前，不会释放锁，可以把锁住的代码块
 * 看作是一个原子操作，必须执行完才会让其他线程获得锁。
 */
public class ReentrantLockTest0 {
    synchronized void m1(){
        for (int i = 0; i <10 ; i++) {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(i);
        }
    }

    synchronized void m2() {
        System.out.println("m2...");
    }

    public static void main(String[] args) {
        ReentrantLockTest0 r = new ReentrantLockTest0();
        new Thread(r::m1).start();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(r::m2).start();
    }
}
