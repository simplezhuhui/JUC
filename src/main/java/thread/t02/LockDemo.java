package thread.t02;

/**
 * synchronized关键字：
 * 1.同步方法：锁定的是当前对象,若是静态方法，锁定的是当前类的Class对象，即xxx.class对象。
 * 2.同步代码块：synchronized(this)锁定的是当前对象
 * 3.不能锁定String、Integer、Long等对象
 * 4.当以对象作为锁的时候，最好用final修饰，不让对象的应用变为另外一个对象（对象的属性改变不影响锁的使用）
 */
public class LockDemo {
    private int count = 10;

    public void get() {
        synchronized (this) {
            count--;
            System.out.println(Thread.currentThread().getName() + ":count" + count);
        }
    }

    public synchronized void get1() {
        count--;
        System.out.println(Thread.currentThread().getName() + ":count" + count);
    }

}
