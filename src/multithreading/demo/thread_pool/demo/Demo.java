package multithreading.demo.thread_pool.demo;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * @author shiyuquan
 * @since 2021/11/1 5:06 下午
 */
public class Demo {
    public static void main(String[] args) throws Exception {

        int tries = 0;
        try {
            // for (;; tries++)
                test();
        } finally {
            System.out.printf("tries = %d%n", tries);
        }
    }

    static void test() throws Exception {
        int minThreads = 1;
        int maxThreads = 1;
        int queueCapacity = 4;

        BlegThreadPoolExecutor pool = new BlegThreadPoolExecutor(
                minThreads, maxThreads,
                1, TimeUnit.HOURS,
                new BlegLinkedBlockingQueue<>(queueCapacity),
                new BlegRejectedExecutionHandler());

        // CompletableFuture.runAsync(() -> pool.setCorePoolSize(maxThreads));
        CompletableFuture<Void> taskBlocker = new CompletableFuture<>();

        try {
            for (int i = queueCapacity + maxThreads + 1; i--> 0; ) {
                // following line sometimes throws a RejectedExecutionException
                System.err.println(i);
                Thread.sleep(1000);
                pool.execute(taskBlocker::join);
            }
        } finally {
            taskBlocker.complete(null);
            pool.shutdown();
        }

        pool.setRejectedExecutionHandler(new BlegThreadPoolExecutor.AbortPolicy());
        pool.execute(taskBlocker::join);

        // for (;;) {

            System.err.println( pool.getCompletedTaskCount());
        // }
    }
}
