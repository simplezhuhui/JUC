package thread.t03;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * CAS又称自旋锁，或者无锁优化，也有人叫乐观锁。
 * 由于一些基本的操作，比如自增等，需要频繁的加锁，于是就设计了原子类(Atomic)等这一些提供特定
 * 操作的类，内部大量使用了CAS操作，号称无锁。
 *
 * AtomicInteger底层使用了unsafe.getAndAddInt(this, valueOffset, 1) + 1;是调用了Unsafe类的方法，再往下就是
 * 调用compareAndSwap()方法，后面的java12或者高版本是weakCompareAndSetInt()方法。
 *
 * 比较并且交换，cas(V,Expected,NewValue):第一个值是要改变的那个值，第二个是期望的值，第三个是要设定的新值。
 * 如果我想改变一个值，先要判断该值是不是我期望中的原先值，如果不是，证明有其他线程改过了，要再循环去判断，直到和
 * 预期值相等再去修改值。
 *
 * cas是cpu的原语支持，也就是说cas操作是cpu指令级别上的支持,中间不能被打断。
 *
 * ABA问题：
 * 如果我获取预期的值相符，有可能是别人修改了一遍后又改回来的，并不能保证该值没被修改过。
 * 对于基本数据类型没什么影响，而对于引用数据类型，每一次修改后，可以添加一个版本号，每次判断的时候连带着版本号一起判断。
 *
 * 形象的比喻：你和女朋友分手了，再过几年又复合了，你并不知道她这几年经历了几个男朋友，哈哈哈。
 * 注：
 * Unsafe相当于C、C++里面的指针，可以直接操作内存。
 */
public class CASDemo {
    AtomicInteger count = new AtomicInteger(0);

    void m() {
        for (int i = 0; i < 10000; i++) {
            count.incrementAndGet();
        }
    }

    public static void main(String[] args) {
        CASDemo casDemo = new CASDemo();
        ArrayList<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            threads.add(new Thread(() -> {
                casDemo.m();
            }, "thread" + i));
        }
        threads.forEach((o) -> {
            o.start();
            try {
                o.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        System.out.println(casDemo.count);
    }
}
