package multithreading.demo.executed_thread_sequentially;

import java.util.concurrent.CyclicBarrier;

/**
 * CyclicBarrier(回环栅栏)
 * 通过它可以实现让一组线程等待至某个状态之后再全部同时执行。
 * 叫做回环是因为当所有等待线程都被释放以后，CyclicBarrier可以被重用。我们暂且把这个状态就叫做barrier，
 * 当调用await()方法之后，线程就处于barrier了。
 *
 * 例子：
 * 公司组织春游,先到的员工就原地等待（await()）,等待所有的员工到达集合地点才能出发，每个人到达后进入barrier状态。
 * 都到达后，唤起大家一起出发去旅行。
 *
 * @author shiyuquan
 * @since 2021/6/24 10:50 上午
 */
public class CyclicBarrierImpl {

    private static CyclicBarrier barrierB = new CyclicBarrier(2);
    private static CyclicBarrier barrierC = new CyclicBarrier(2);

    public static void main(String[] args) {
        Thread ta = new Thread(() -> {
            try {
                System.err.println("A");
                barrierB.await();
            } catch (Exception e) {

            }
        });

        Thread tb = new Thread(() -> {
            try {
                barrierB.await();
                System.err.println("B");
                barrierC.await();
            } catch (Exception e) {

            }
        });

        Thread tc = new Thread(() -> {
            try {
                barrierC.await();
                System.err.println("C");
            } catch (Exception e) {

            }
        });

        ta.start();
        tb.start();
        tc.start();
    }

}
