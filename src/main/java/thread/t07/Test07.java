package thread.t07;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * 面试题：实现一个容器，提供两个方法add/size,写两个线程：
 * 线程1：添加10个元素到容器中
 * 线程2：实时监控元素个数，当个数到5个时，线程2给出提示并结束。
 * 使用locksupport实现
 */
public class Test07 {
    List lists = new ArrayList<>();

    public void add(Object o) {
        lists.add(o);
    }

    public int size() {
        return lists.size();
    }

    static Thread t1 = null, t2 = null;

    public static void main(String[] args) {
        Test07 c = new Test07();
        //先启动t2
        t2 = new Thread(() -> {
            System.out.println("t2 启动");
            if (c.size() != 5) {
                LockSupport.park();
            }
            System.out.println("t2 结束");
            LockSupport.unpark(t1);
        }, "t2");
        t2.start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //启动t1
        t1 = new Thread(() -> {
            System.out.println("t1 启动");
            for (int i = 0; i < 10; i++) {
                c.add(new Object());
                System.out.println("add " + i);
                if (c.size() == 5) {
                    //暂停t1线程
                    LockSupport.unpark(t2);
                    LockSupport.park();
                }
//                try {
//                    TimeUnit.SECONDS.sleep(1);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
            }
        }, "t1");
        t1.start();


    }
}