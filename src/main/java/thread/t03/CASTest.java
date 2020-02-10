package thread.t03;

import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;

/**
 * 我们使用cas，是由于它的效率更高，测试一下。
 * emm...
 * 事实证明sunchronized并不一定比cas慢。。。。
 */
public class CASTest {
    static long count1 = 0L;
    static AtomicLong count2 = new AtomicLong(0L);


    public static void main(String[] args) throws InterruptedException {
        Thread[] threads = new Thread[1000];


        for (int i = 0; i <threads.length ; i++) {
            threads[i]=new Thread(()->{
                for (int j = 0; j <100000 ; j++) {
                    count2.incrementAndGet();
                }
            });
        }
      long  start = System.currentTimeMillis();
        for(Thread t : threads ) t.start();

        for (Thread t : threads) t.join();
      long  end=System.currentTimeMillis();
        System.out.println("Atomic:"+count2.get()+" time"+(end-start));

        Object lock = new Object();
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(() -> {
                for (int k = 0; k < 100000; k++)
                    synchronized (lock) {
                        count1++;
                    }
            });
        }

        start = System.currentTimeMillis();
        for(Thread t : threads ) t.start();

        for (Thread t : threads) t.join();
        end=System.currentTimeMillis();
        System.out.println("syn:"+"count1="+count1+" time="+(end-start));
        //=====================================

    }
}

