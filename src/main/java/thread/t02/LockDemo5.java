package thread.t02;

/**
 * 程序执行过程中，如果出现异常，默认锁会释放,其他线程会进入同步代码中，造成程序乱入。
 */
public class LockDemo5 {
    int count = 0;

    public synchronized void m() {
        while (true) {
            count++;
            System.out.println(Thread.currentThread().getName() + ": " + count);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (count == 5) {
                int i = count / 0;
                System.out.println(i);
            }
        }
    }

    public static void main(String[] args) {
        LockDemo5 t = new LockDemo5();

        new Thread(() -> {
            t.m();
        }, "A").start();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(() -> {
            t.m();
        }, "B").start();
    }
}
