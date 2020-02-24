package thread.t07;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 面试题：实现一个容器，提供两个方法add/size,写两个线程：
 * 线程1：添加10个元素到容器中
 * 线程2：实时监控元素个数，当个数到5个时，线程2给出提示并结束。
 * 下面这个程序不合格，线程之间size不可见,没有添加同步
 */
public class Test01 {
    List lists = new ArrayList();

    public void add(Object o) {
        lists.add(0);
    }

    public int size() {
        return lists.size();
    }

    public static void main(String[] args) {
        Test01 c = new Test01();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                c.add(new Object());
                System.out.println("add " + i);
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "t1").start();

        new Thread(() -> {
            while (true) {
                if (c.size() == 5) {
                    break;
                }
            }
            System.out.println("t2 结束！");
        }, "t2").start();

    }
}
