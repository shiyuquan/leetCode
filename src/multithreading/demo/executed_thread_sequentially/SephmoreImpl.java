package multithreading.demo.executed_thread_sequentially;

import java.util.concurrent.Semaphore;

/**
 * SephmoreImpl(信号量):
 * Semaphore 是一个计数信号量,从概念上将，Semaphore包含一组许可证，每个acquire()方法都会阻塞，
 * 直到获取一个可用的许可证,每个release() 方法都会释放持有许可证的线程，并且归还Semaphore一个
 * 可用的许可证。实际上并没有真实的许可证对象供线程使用，Semaphore只是对可用的数量进行管理维护。
 *
 * acquire():当前线程尝试去阻塞的获取1个许可证,此过程是阻塞的,当前线程获取了1个可用的许可证，则
 *           会停止等待，继续执行。
 *
 * release():当前线程释放1个可用的许可证。
 *
 * 应用场景:
 * Semaphore可以用来做流量分流，特别是对公共资源有限的场景，比如数据库连接。假设有这个的需求，读
 * 取几万个文件的数据到数据库中，由于文件读取是IO密集型任务，可以启动几十个线程并发读取，但是数据
 * 库连接数只有10个，这时就必须控制最多只有10个线程能够拿到数据库连接进行操作。这个时候，就可以使
 * 用Semaphore做流量控制。
 *
 * @author shiyuquan
 * @since 2021/6/24 11:02 上午
 */
public class SephmoreImpl {

    private static Semaphore semaphoreB = new Semaphore(1);
    private static Semaphore semaphoreC = new Semaphore(1);

    public static void main(String[] args) throws Exception {
        semaphoreB.acquire();
        semaphoreC.acquire();
        Thread ta = new Thread(() -> {
            try {
                System.err.println("A");
                semaphoreB.release();
            } catch (Exception e) {

            }
        });

        Thread tb = new Thread(() -> {
            try {
                semaphoreB.acquire();
                System.err.println("B");
                semaphoreC.release();
            } catch (Exception e) {

            }
        });

        Thread tc = new Thread(() -> {
            try {
                semaphoreC.acquire();
                System.err.println("C");
            } catch (Exception e) {

            }
        });

        ta.start();
        tb.start();
        tc.start();
    }

}
