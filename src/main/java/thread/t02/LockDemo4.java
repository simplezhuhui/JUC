package thread.t02;

/**
 * synchronized是可重入锁，一个同步方法可以调用另外一个同步方法。
 * 同一个线程已经拥有了某个对象的锁，再次申请的时候仍然会得到该对象的锁。
 * 注意可重入锁，必须是在同一个线程中。
 */
public class LockDemo4 {

    public synchronized void m1() {
        System.out.println("m1 start");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        m2();
        System.out.println("m1 end");
    }

    public synchronized void m2() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("m2 end");
    }

    public static void main(String[] args) {
        LockDemo4 lockDemo4 = new LockDemo4();
        new Thread(()->{
            lockDemo4.m1();
        }).start();
    }
}
