package multithreading.demo.thread_pool;

import java.util.concurrent.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author shiyuquan
 * Create Time: 2019/7/16 20:49
 */
public class T_Runnable {

    public static void main(String[] args) {
        // ThreadFactory threadFactory = new ThreadFactory;
        Logger logger = Logger.getLogger("s");
        logger.log(Level.WARNING,"main start");
        // System.out.println("main start");
        // ExecutorService threadPool = Executors.newSingleThreadExecutor();
        ExecutorService threadPool = Executors.newSingleThreadExecutor();
        Future future = threadPool.submit(new MyRunnable());

        try {
            System.out.println(future.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } finally {
            threadPool.shutdown();
        }
        System.out.println("main end");
    }
}

class MyRunnable implements Runnable {

    @Override
    public void run() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("MyRunnable");
    }
}
