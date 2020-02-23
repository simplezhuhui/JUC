package thread.t05;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读写锁，其实就是共享锁和排它锁,当进行读的的时候，多个读线程之间是共享锁，不堵塞，和写线程互斥，写线程堵塞，只有等读线程结束才能进入。
 * 当线程在进行写操作的时候，读线程堵塞，必须写完才能读。
 *
 */
public class TestReadWriteLock {
    static Lock lock = new ReentrantLock();
    private static int value;

    static ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    static Lock readLock = readWriteLock.readLock();//读锁
    static Lock writeLock = readWriteLock.writeLock();//写锁

    public static void read(Lock lock) {
        try {
            lock.lock();
            Thread.sleep(1000);
            System.out.println("read over!");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public static void write(Lock lock, int v) {
        try {
            lock.lock();
            Thread.sleep(1000);
            value = v;
            System.out.println("write over!");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
//        Runnable readR = () -> read(lock);
        Runnable readR=()->read(readLock);

//        Runnable writeR = () -> write(lock, new Random().nextInt());
        Runnable writeR=()->write(writeLock,new Random().nextInt());

        for (int i = 0; i < 18; i++) {
            new Thread(readR).start();
        }
        for (int i = 0; i < 2; i++) {
            new Thread(writeR).start();
        }
    }
}
