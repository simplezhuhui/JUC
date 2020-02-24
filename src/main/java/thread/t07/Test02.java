package thread.t07;

import java.util.ArrayList;
import java.util.List;

/**
 * 面试题：实现一个容器，提供两个方法add/size,写两个线程：
 * 线程1：添加10个元素到容器中
 * 线程2：实时监控元素个数，当个数到5个时，线程2给出提示并结束。
 * 加了volatile修饰引用类型之后，只能保证引用可见，它指向的对象的成员变量值发生改变的话，是不可见的。
 * 所以volatile尽量去修饰普通类型的值。
 * 程序执行失败
 */
public class Test02 {
 volatile List lists = new ArrayList();

    public void add(Object o) {
        lists.add(0);
    }

    public int size() {
        return lists.size();
    }

    public static void main(String[] args) {
        Test02 c = new Test02();
        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                c.add(new Object());
                System.out.println("add "+i);
                /*try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }*/
            }
        },"t1").start();

        new Thread(()->{
            while (true){
                if(c.size()==5){
                    break;
                }
            }
            System.out.println("t2 结束！");
        },"t2").start();

    }
}
