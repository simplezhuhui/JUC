package thread.t05;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * CyclicBarrier:循环栅栏，有一个栅栏，等人满了，把栅栏推倒，放出去。
 * 出去之后栅栏又重新起来，再来人，满了之后再推倒。
 * 每个线程都会执行await()方法，人满了之后，放行。
 */
public class TestCyclicBarrier {
    public static void main(String[] args) {
        CyclicBarrier barrier = new CyclicBarrier(20, () -> System.out.println("满员！进行下一步操作"));
        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                try {
                    barrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }).start();
        }

    }
}
