package thread.t02;

/**
 * 不加锁，下面程序会出现线程安全问题
 */
public class LockDemo2 implements Runnable {
    private  int count = 100;

    @Override
    public /*synchronized*/ void run() {
        count--;
        System.out.println(Thread.currentThread().getName()+" count= "+count);
    }

    public static void main(String[] args) {
        LockDemo2 run = new LockDemo2();
        for (int i = 0; i <100 ; i++) {
            new Thread(run,"thread"+i).start();
        }
    }
}
