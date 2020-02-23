package thread.t05;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * 信号灯,可以往里面传一个允许的数量，表示允许有几个线程来获取信号
 * acquire()方法是阻塞方法，如果获取不到信号，就停在这里，每次调用成功后，信号量减1，线程结束后release(),数量恢复。
 * 含义就是限流，允许有几个线程同时执行。
 */
public class TestSemaphore {
    public static void main(String[] args) {
        Semaphore s = new Semaphore(2);

        new Thread(() -> {
            try {
                s.acquire();
                System.out.println("T1 running...");
                TimeUnit.SECONDS.sleep(2);
                System.out.println("T1 running...");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                s.release();
            }
        }).start();

        new Thread(() -> {
            try {
                s.acquire();
                System.out.println("T2 running...");
                TimeUnit.SECONDS.sleep(1);
                System.out.println("T2 running...");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                s.release();
            }
        }).start();

    }
}
