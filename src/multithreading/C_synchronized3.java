package multithreading;

/**
 * 同步和非同步方法可以同时调用吗
 * -- 可以同时调用
 * @author shiyuquan
 * Create Time: 2019/7/2 14:55
 */
public class C_synchronized3 {

    public synchronized void m1() {
        System.out.println(Thread.currentThread().getName() + " m1 start...");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " m1 end");
    }

    public void m2() {
        System.out.println(Thread.currentThread().getName() + " m2 start...");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " m2 end");
    }

    public static void main(String[] args) {
        C_synchronized3 c = new C_synchronized3();
        new Thread(c::m1, "t1").start();
        new Thread(() -> c.m2(), "t2").start();
    }
}
