package thread.t01;

/**
 * 线程的生命周期以及几个常用的方法
 * 生命周期：（图state.png）new、ready、running、blocked、waiting、timed Waiting、terminated
 * 1.new出来一个线程时，但还没有调用start()方法时，处于新建状态（new）
 * 2.线程对象调用start()方法后，进入Runnable状态，交给操作系统去选择执行哪条线程。
 *   Runnable内部包含两个状态，ready就绪状态，running运行状态。
 *  当在就绪队列中的线程等待被操作系统调用时，处于就绪状态。操作系统调度执行到某条线程时，该线程处于运行状态，
 *  时间片用完后，又会被扔到就绪队列中，回到就绪状态,等待下一次调用。
 * 3.就绪和运行状态之间也会有一些状态的变迁。
 *  3.1当线程执行同步方法或者同步代码块时，还未获得锁时，进入阻塞状态（Blocked），获得锁后进入就绪状态，等待cpu调度执行。
 *  3.2当调用Thread.sleep(time)、o.wait(time)、t.join(time)、LockSupport.parkUntil()等方法会进入Timed Waiting状态，
 *     指定时间过后，或者调用o.notify()/notifyAll(),进入就绪或者阻塞状态。
 *  3.3当调用o.wait()、t.join()、LockSupport.park()方法时，会进入waiting状态，调用o.notify()/notifyAll()后进入就绪或者阻塞状态
 * 注意：由于wait和notify只能在同步方法或者同步代码块中使用，所有当调用notify()方法后，线程先进入阻塞状态，获得锁后再进入就绪状态。
 * 4.线程正常运行完或者出现异常被停止，进入死亡状态。
 *
 * yield()：调用后，线程从运行状态进入就绪状态，相当于谦让一下，但也有可能下一次还是它被cpu选中，进入运行状态。
 * sleep()：线程睡眠指定时间后，重新进入就绪状态。在睡眠期间，若持有锁，不会释放。
 * join():在一个线程汇总调用另一个线程的join()方法后，当前线程进入waiting状态，等待另外一个线程执行完后，重新进入就绪状态。
 */
public class ThreadAPI {
    public static void main(String[] args) {
        //几个常用的方法yield()/join()/sleep()不演示了，比较简单:
        new Thread(()->{
            System.out.println("hello");
        }).start();
    }

}
