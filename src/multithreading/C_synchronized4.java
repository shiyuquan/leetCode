package multithreading;

import java.sql.Time;
import java.util.concurrent.TimeUnit;

/**
 * 一个同步方法可以调用另外一个同步方法，一个线程已经拥有某个对象的锁，在此申请的时候仍然会得到该对象的锁。
 * 也就是说synchronized获得的锁是可以重入的。
 * 这种情景在继承中可能发生，子类访问父类的同步方法时
 * @author shiyuquan
 * Create Time: 2019/7/2 15:52
 */
public class C_synchronized4 {

    synchronized void m1() {
        System.out.println("m1 start");

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        m2();
    }

    public synchronized  void m2() {
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("m2");
    }

    public static void main(String[] args) {
        new T().m1();
    }
}

class T extends C_synchronized4 {
    synchronized void m1() {
        System.out.println("T m1 start");
        super.m1();
        System.out.println("T m1 end");
    }
}
