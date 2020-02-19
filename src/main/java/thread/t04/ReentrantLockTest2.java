package thread.t04;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * synchronized锁，可以由Reentrantlock代替，它也是可重入锁。
 * 但是synchronized能够自动释放锁（包括遇到异常），lock必须手动解锁，要放在finally语句中保证释放，不然如果在执行
 * 的过程中出了问题，其他线程就永远拿不到这把锁了。
 *
 */
public class ReentrantLockTest2 {
    Lock lock = new ReentrantLock();

    void m1() {
        lock.lock();
        try {
            for (int i = 0; i < 10; i++) {
                TimeUnit.SECONDS.sleep(1);
                System.out.println(i);
                //if (i == 2) m2();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

     void m2() {
         try {
             lock.lock();
             System.out.println("m2...");
         } catch (Exception e) {
             e.printStackTrace();
         } finally {
             lock.unlock();
         }
     }

    public static void main(String[] args) {
        ReentrantLockTest2 r = new ReentrantLockTest2();
        new Thread(r::m1).start();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(r::m2).start();
    }
}
