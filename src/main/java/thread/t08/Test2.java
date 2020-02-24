package thread.t08;
/**
 * 交叉打印A1B2C3D4E5...Z26
 */
public class Test2 {
    public static void main(String[] args) {
        final Object lock = new Object();

        new Thread(() -> {
            synchronized (lock) {
                for (int i = 65; i < 91; i++) {
                   /* lock.notify();
                    try {
                        lock.wait();//让出锁
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println((char) i);*/
                    System.out.println((char) i);
                    lock.notify();
                    try {
                        lock.wait();//让出锁
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                lock.notify();
            }
        }, "t1").start();

        new Thread(() -> {
            synchronized (lock) {
                for (int i = 1; i < 27; i++) {
                    System.out.println(i);
                    try {
                        lock.notify();
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                //lock.notify();
            }
        }, "t2").start();
    }
}
