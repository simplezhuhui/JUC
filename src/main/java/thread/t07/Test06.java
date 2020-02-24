package thread.t07;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * 面试题：实现一个容器，提供两个方法add/size,写两个线程：
 * 线程1：添加10个元素到容器中
 * 线程2：实时监控元素个数，当个数到5个时，线程2给出提示并结束。
 * 如果注释掉休眠的代码就会出现问题，所以该程序也不可行。
 * 当线程t1拉开门闩后，线程t2并不会执行，而是t1继续执行。
 * 需要增加一个门栓，程序如下：
 */
public class Test06 {
    List lists = new ArrayList<>();

    public void add(Object o) {
        lists.add(o);
    }

    public int size() {
        return lists.size();
    }

    public static void main(String[] args) {
        Test06 c = new Test06();
        CountDownLatch latch1 = new CountDownLatch(1);
        CountDownLatch latch2 = new CountDownLatch(1);
        //先启动t2
        new Thread(() -> {
            System.out.println("t2 启动");
            if (c.size() != 5) {
                try {
                    latch1.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("t2 结束");
            latch2.countDown();
        }, "t2").start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //启动t1
        new Thread(() -> {
            System.out.println("t1 启动");
            for (int i = 0; i < 10; i++) {
                c.add(new Object());
                System.out.println("add " + i);
                if (c.size() == 5) {
                    //暂停t1线程
                    latch1.countDown();
                    try {
                        latch2.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
//                try {
//                    TimeUnit.SECONDS.sleep(1);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
            }
        }, "t1").start();


    }
}