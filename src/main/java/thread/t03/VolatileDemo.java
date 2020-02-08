package thread.t03;

/**
 * volatile关键字:
 * 1.保证可见性:每个线程都有自己的工作内存，对变量操作的时候，会先从主内存（堆内存）中复制一份过来，
 * 修改完后再立即写回主内存。但如果不加volatile的话，修改回去后，其他线程也不可见，操作的依然是上一次取到的值。
 * 是基于MESI高速缓存一致性协议实现的。
 * 2.禁止指令重排：加了volatile之后，对该对象上的指令重排序是不允许的。
 * 扩展：在DCL单例模式中用到（INSTANCE=new xxx():该语句要分三步执行，先申请内存，再进行初始化，最后把地址赋给INSTANCE）
 * 3.不保证原子性。
 */
public class VolatileDemo {
    private /*volatile*/ boolean running = true;

    public void m() {
        System.out.println("m start");
        while (running) {
        }
        System.out.println("m end");
    }

    public static void main(String[] args) {
        VolatileDemo v = new VolatileDemo();
        new Thread(() -> {
            v.m();
        }).start();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        v.running = false;
    }
}
