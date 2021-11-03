package multithreading.demo.executed_thread_sequentially;

/**
 * 线程顺序执行
 * synchronized 和 volatile 的实现方法
 * 默认下都wait(), 等待满足条件，进行具体方法执行
 *
 * @author shiyuquan
 * @since 2021/6/23 12:34 下午
 */
public class SynchronizedAndVolatileImpl {

    private volatile int ordeNum = 1;

    // private AtomicInteger orderNum = 1;

    private synchronized void A() {
        try {
            while (ordeNum != 1) {
                wait();
            }
            System.err.println("A");
            ordeNum = 2;
            notifyAll();
        } catch (Exception e) {

        }
    }

    private synchronized void B() {
        try {
            while (ordeNum != 2) {
                wait();
            }
            System.err.println("B");
            ordeNum = 3;
            notifyAll();
        } catch (Exception e) {

        }
    }

    private synchronized void C() {
        try {
            while (ordeNum !=3 ) {
                wait();
            }
            System.err.println("C");
            ordeNum = 1;
            notifyAll();
        } catch (Exception e) {

        }
    }

    public static void main(String[] args) {
        SynchronizedAndVolatileImpl saa = new SynchronizedAndVolatileImpl();


        Thread thread1 = new Thread(saa::A);
        Thread thread2 = new Thread(saa::B);
        Thread thread3 = new Thread(saa::C);


        thread1.start();
        thread2.start();
        thread3.start();
    }

}
