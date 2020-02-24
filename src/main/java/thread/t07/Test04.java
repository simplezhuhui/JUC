package thread.t07;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 面试题：实现一个容器，提供两个方法add/size,写两个线程：
 * 线程1：添加10个元素到容器中
 * 线程2：实时监控元素个数，当个数到5个时，线程2给出提示并结束。
 * 下面程序执行成功，刚开始t2线程先执行，判断集合中数量没有5个，t2进入阻塞状态，释放锁。
 * 然后t1执行，当集合中数量到达5个时，t1调用notify()唤醒t1，并且调用wait()释放锁，进入阻塞状态
 * t2获得锁之后执行，执行结束后调用notify()唤醒t1继续执行。
 */
public class Test04 {
    List lists = new ArrayList<>();

    public void add(Object o) {
        lists.add(o);
    }

    public int size() {
        return lists.size();
    }

    public static void main(String[] args) {
        Test04 c = new Test04();
        final Object lock = new Object();
        //先启动t2，再启动t1
        new Thread(() -> {
            synchronized (lock) {
                System.out.println("t2 启动");
                if (c.size() != 5) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("t2 结束");
                lock.notify();
            }
        }, "t2").start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(() -> {
            System.out.println("t1 启动");
            synchronized (lock) {
                for (int i = 0; i < 10; i++) {
                    c.add(new Object());
                    System.out.println("add " + i);
                    if (c.size() == 5) {
                        lock.notify();
                        //释放锁，让t2执行
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
//                    try {
//                        TimeUnit.SECONDS.sleep(1);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
                }
            }

        }, "t1").start();
    }
}
