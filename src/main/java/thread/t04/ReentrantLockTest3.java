package thread.t04;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * reentrantlock虽然和synchronized功能差不多，但还是有一些更强大的功能。比如tryLock
 * 可以使用tryLock进行尝试锁，不管锁定与否，方法都将继续执行，synchronized如果拿不到锁的话，就阻塞了。
 * 但是用reentrantlock你可以自己决定要不要wait
 *
 */
public class ReentrantLockTest3 {
    Lock lock = new ReentrantLock();

    void m1() {
        lock.lock();
        try {
            for (int i = 0; i < 3; i++) {
                TimeUnit.SECONDS.sleep(1);
                System.out.println(i);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

     void m2() {
        boolean locked=false;
         try {
             locked= lock.tryLock(5,TimeUnit.SECONDS);
             System.out.println("m2..."+locked);
         } catch (Exception e) {
             e.printStackTrace();
         } finally {
             if(locked){
                 lock.unlock();
             }
         }
     }

    public static void main(String[] args) {
        ReentrantLockTest3 r = new ReentrantLockTest3();
        new Thread(r::m1).start();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(r::m2).start();
    }
}
