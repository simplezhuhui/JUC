package thread.t04;

import javax.lang.model.element.VariableElement;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * reentrantlock还可以指定公平锁,true为公平锁，意思谁在前面就可让谁执行，而不是谁后来了立即可以去抢，
 * 它会先检查是队列里是否有等待队列，如果有的话它会进入等待队列等别人先运行。
 */
public class ReentrantLockTest5 {
    Lock lock = new ReentrantLock(true);
//    Lock lock = new ReentrantLock();

    void m1() {
        for (int i = 0; i < 100; i++) {
            try {
                lock.lock();
                System.out.println(Thread.currentThread().getName() + " 获得锁");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) {
        ReentrantLockTest5 r = new ReentrantLockTest5();
        new Thread(() -> {
            r.m1();
        },"线程1").start();

        new Thread(() -> {
            r.m1();
        },"线程2").start();
    }

}
