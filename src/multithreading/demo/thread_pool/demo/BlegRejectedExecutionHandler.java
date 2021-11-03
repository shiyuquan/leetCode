package multithreading.demo.thread_pool.demo;

/**
 * 自定义拒绝策略
 * 对失败线程进行后置处理
 *
 * @author shiyuquan
 * @since 2021/1/21 11:07 上午
 */
public class BlegRejectedExecutionHandler implements IRejectedExecutionHandler {

    @Override
    public void rejectedExecution(Runnable r, BlegThreadPoolExecutor executor) {
        System.err.println("BlegRejectedExecutionHandler");
        if (r instanceof BlegTask) {
            BlegTask t = (BlegTask) r;
        }
    }
}
