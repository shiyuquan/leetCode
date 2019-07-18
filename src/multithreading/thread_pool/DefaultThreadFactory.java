package multithreading.thread_pool;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author shiyuquan
 * Create Time: 2019/7/16 22:01
 */
public class DefaultThreadFactory implements ThreadFactory {
    /**
     * 线程池序号
     */
    private static final AtomicInteger poolNumber = new AtomicInteger(1);

    /**
     * 线程序号
     */
    private final AtomicInteger threadNumber = new AtomicInteger(1);

    private final ThreadGroup threadGroup;

    private String threadName;

    DefaultThreadFactory() {
        SecurityManager s = System.getSecurityManager();
        threadGroup = (s != null) ? s.getThreadGroup() :
                Thread.currentThread().getThreadGroup();
    }

    @Override
    public Thread newThread(Runnable r) {
        return null;
    }

    public DefaultThreadFactory build(ThreadFactoryBuilder builder) {
        return new DefaultThreadFactory();
    }

    public static AtomicInteger getPoolNumber() {
        return poolNumber;
    }

    public AtomicInteger getThreadNumber() {
        return threadNumber;
    }

    public ThreadGroup getThreadGroup() {
        return threadGroup;
    }

    public String getThreadName() {
        return threadName;
    }

    public void setThreadName(String threadName) {
        this.threadName = threadName;
    }

    public DefaultThreadFactory setName(String threadName) {
        this.threadName = threadName;
        return this;
    }
}
