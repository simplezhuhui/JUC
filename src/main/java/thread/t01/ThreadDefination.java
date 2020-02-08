package thread.t01;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 * 多线程的定义及启动方式
 * 先明白几个定义：
 * 1.程序：比如电脑里.exe文件就是一个程序，如qq...
 * 2.进程：程序运行起来就是一个进程，相对于程序来说有一个动态的概念
 * 3.线程：是进程里面最小的执行单元，也可以理解为一个程序里面不同的执行路径。
 * （关于线程最容易理解的例子：word文档，一边在编辑的时候，后台在进行着自动保存，如果是单线程的话，就很不友好）
 * 问：线程启动的几种方式？
 * （1）继承Thread类，重写run方法。
 * （2）实现Runnable接口，实现run方法。
 * （3）线程池，其实也是用的这两种之一。Executors.newCachedThreadPool()或者FutureTask+Callable
 * <p>
 * 启动线程必须调用start()方法，若直接调用run()方法，本质还是单线程的一个对象方法调用，并不是多线程。
 */
public class ThreadDefination {
    static class MyThread extends Thread {
        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                System.out.println(Thread.currentThread().getName() + ":" + i);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class MyRunnable implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                System.out.println(Thread.currentThread().getName() + ":" + i);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class MyCall implements Callable<String> {

        @Override
        public String call() {
            System.out.println(Thread.currentThread().getName() + "Mycall");
            return "success";
        }
    }

    public static void main(String[] args) {
        //启动线程
        //1 继承类
        new MyThread().start();
        //2 实现接口
        new Thread(new MyRunnable()).start();
        //3 实现接口lambda写法
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                System.out.println(Thread.currentThread().getName() + ":" + i);
            }
        }).start();
        //4
        new Thread(new FutureTask<String>(new MyCall())).start();
        //5 线程池
        ExecutorService service = Executors.newCachedThreadPool();
        service.execute(() ->
                System.out.println(Thread.currentThread().getName() + "hello ThreadPool")
        );
        service.shutdown();
    }
}
