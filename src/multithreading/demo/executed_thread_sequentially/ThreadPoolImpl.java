package multithreading.demo.executed_thread_sequentially;

import multithreading.demo.thread_pool.demo.BlegThreadPool;

/**
 * 线程池的方式
 * 思想是创建 先进先出的 线程池，顺序跑任务
 *
 * @author shiyuquan
 * @since 2021/6/24 10:09 上午
 */
public class ThreadPoolImpl {

    public static void main(String[] args) {
        BlegThreadPool.getInstance().execute(new A());
        BlegThreadPool.getInstance().execute(new B());
        BlegThreadPool.getInstance().execute(new C());
        BlegThreadPool.getInstance().shutdown();
    }

}
