package multithreading.demo.thread_pool;

import java.util.concurrent.*;

/**
 * 认识callable，对runnable进行了扩展
 * 对callable 的调用可以有返回值
 *
 * @author shiyuquan
 * Create Time: 2019/7/16 20:35
 */
public class T_Callable{

    public static void main(String[] args) {
        ThreadFactory factory = new DefaultThreadFactory().setName("s");
        System.out.println("main start");
        ExecutorService threadPool = Executors.newCachedThreadPool();

        Future<String> future = threadPool.submit(new MyCallable());

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

class MyCallable implements Callable<String> {

    @Override
    public String call() throws Exception {
        Thread.sleep(3000);
        System.out.println("MyCallable");
        return "MyCallable";
    }
}
