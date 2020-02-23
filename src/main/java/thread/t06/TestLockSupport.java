package thread.t06;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * LockSupport的park()和unpark()方法可以更灵活的实现线程的阻塞和唤醒。
 * unpark可以先于park()调用。
 */
public class TestLockSupport {
    public static void main(String[] args) {
        Thread t = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                System.out.println(i);
                if (i == 5) {
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
        LockSupport.unpark(t);//此时线程t还没有被阻塞
    }
}
