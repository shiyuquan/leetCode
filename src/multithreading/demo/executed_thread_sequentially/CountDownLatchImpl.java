package multithreading.demo.executed_thread_sequentially;

import java.util.concurrent.CountDownLatch;

/**
 * CountDownLatch 位于java.util.concurrent包下，利用它可以实现类似计数器的功能。
 *
 * @author shiyuquan
 * @since 2021/6/24 10:37 上午
 */
public class CountDownLatchImpl {

    /** A 的倒计数 */
    private static CountDownLatch cA = new CountDownLatch(1);
    /** B 的倒计数 */
    private static CountDownLatch cB = new CountDownLatch(1);

    public static void main(String[] args) {
        Thread ta = new Thread(() -> {
            System.err.println("A");
            cA.countDown();
        });

        Thread tb = new Thread(() -> {
            try {
                cA.await();
                System.err.println("B");
                cB.countDown();
            } catch (Exception e) {

            }
        });

        Thread tc = new Thread(() -> {
            try {
                cB.await();
                System.err.println("C");
            } catch (Exception e) {

            }
        });

        ta.start();
        tb.start();
        tc.start();
    }

}
