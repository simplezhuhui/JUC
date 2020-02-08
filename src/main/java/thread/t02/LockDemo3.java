package thread.t02;

/**
 * 同步方法和非同步方法可以同时执行
 */
public class LockDemo3 {

    public synchronized void synMethod() {
        System.out.println("同步方法开始执行=========");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("同步方法结束=========");
    }

    public void normalMethod() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("非同步方法结束=========");
    }

    public static void main(String[] args) {
        LockDemo3 lockDemo3 = new LockDemo3();
        new Thread(() -> {
            lockDemo3.synMethod();
        }).start();
        new Thread(() -> {
            lockDemo3.normalMethod();
        }).start();
    }
}
