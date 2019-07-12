package multithreading;

/**
 * synchronized 关键字
 * 对某个对象加锁
 * 锁是加在对象的内存上，对象的内存里放着个标识，标识着该对象已经上锁。请求锁时去查看是不是有锁，如果有锁，
 * 请求的线程将锁拿走，别的线程再请求的时候发现请求不到锁，会等待锁的释放。
 * @author shiyuquan
 * Create Time: 2019/7/2 13:50
 */
public class C_synchronized {
    private int count = 10;
    private static int count2 = 10;
    private Object o = new Object();

    private void run() {
        synchronized (o) {
            count--;
            System.out.println(Thread.currentThread().getName() + " count = " + count);
        }
    }

    /**
     * 给this对象上锁
     */
    private void run1() {
        synchronized (this) {
            count--;
            System.out.println(Thread.currentThread().getName() + " count = " + count);
        }
    }

    /**
     * 相当于 synchronized（this）
     */
    private synchronized void run2() {
        count--;
        System.out.println(Thread.currentThread().getName() + " count = " + count);
    }

    /**
     * 这里相当于 synchronized(C_synchronized.class)
     */
    private synchronized static void run3() {
        count2--;
        System.out.println(Thread.currentThread().getName() + " count = " + count2);
    }

    /**
     * 这里考虑为什么不能用this关键字？
     * Static方法是类方法，先于任何的实例（对象）存在。而this指代的是当前的对象。
     * 所以在静态方法是用不了this的，因为加载的时候这个对象还不存在
     * Static方法在类加载时就已经存在了（JAVA虚拟机初始化时），但是对象是在创建时才在内存中生成。
     */
    private static void run4() {
        synchronized (C_synchronized.class) {
            count2--;
        }
    }

    public static void main(String[] args) {
        C_synchronized c = new C_synchronized();
        c.run();
        c.run();
        c.run();
    }
}
