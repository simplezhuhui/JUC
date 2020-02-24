package thread.t08;

import java.util.concurrent.locks.LockSupport;

/**
 * 交叉打印A1B2C3D4E5...Z26
 */
public class Test1 {
    static Thread t1=null,t2=null;

    public static void main(String[] args) {
        t1=new Thread(()->{
            for (int i = 65; i <91 ; i++) {
                System.out.println((char)i);
                LockSupport.unpark(t2);//唤醒另一个
                LockSupport.park();//自己阻塞
            }
        },"t1");
        t2=new Thread(()->{
            for (int i = 1; i <27 ; i++) {
                LockSupport.park();//自己先阻塞
                System.out.println(i);
                LockSupport.unpark(t1);//唤醒另一个
            }
        },"t1");

        t1.start();
        t2.start();
    }
}
