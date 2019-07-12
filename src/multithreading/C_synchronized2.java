package multithreading;

/**
 * 分析这程序的输出
 * @author shiyuquan
 * Create Time: 2019/7/2 14:27
 */
public class C_synchronized2 implements Runnable {

    private int count = 10;

    @Override
    public synchronized void run() {
        count--;
        System.out.println(Thread.currentThread().getName()+ " count = " + count);
    }

    /**
     * 对不加synchronized 的run方法的运行，得到的结果不是预想的规则有序的，得到的输出不规整，如我某次的运行结果：
     * thread0 count = 9
     * thread2 count = 7
     * thread3 count = 5
     * thread1 count = 8
     * thread4 count = 5
     * 因为5个线程对同一个数字进行操作，而++、--等操作不是原子性的。可能在上一个线程进行到一半，新的线程却插进来，并且执行完了，
     * 导致上一个线程的输出不正确。
     *
     * 对加了锁的run方法进行运行，得到的结果就很整齐，如某次的运行结果：
     * thread0 count = 9
     * thread1 count = 8
     * thread2 count = 7
     * thread4 count = 6
     * thread3 count = 5
     * synchronized关键字加在方法上，相当于对this对象上锁，所以再运行方法相当于每次的操作都是原子的，所以得到的结果很整齐
     * @param args
     */
    public static void main(String[] args) {
        C_synchronized2 c = new C_synchronized2();
        for (int i = 0; i < 5; i++) {
            new Thread(c, "thread" + i).start();
        }
    }
}
