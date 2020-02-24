package thread.t07;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 面试题：实现一个容器，提供两个方法add/size,写两个线程：
 * 线程1：添加10个元素到容器中
 * 线程2：实时监控元素个数，当个数到5个时，线程2给出提示并结束。
 * 下面程序执行错误，原因是notify()方法不释放锁，当t1线程调用了notify方法后，并没有释放当前的锁，所以t1
 * 还是会继续执行下去，直到t1执行完，t2才会接着执行。
 */
public class Test03 {
    List lists = new ArrayList<>();

    public void add(Object o) {
        lists.add(o);
    }

    public int size() {
        return lists.size();
    }

    public static void main(String[] args) {
        Test03 c = new Test03();
        final Object lock = new Object();
        //先启动t2，再启动t1
        new Thread(()->{
            synchronized (lock){
                System.out.println("t2 启动");
                if(c.size()!=5){
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("t2 结束");
            }
        },"t2").start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(()->{
            System.out.println("t1 启动");
            synchronized (lock){
                for (int i = 0; i <10 ; i++) {
                    c.add(new Object());
                    System.out.println("add "+i);
                    if(c.size()==5){
                        lock.notify();
                    }
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

        },"t1").start();
    }
}
