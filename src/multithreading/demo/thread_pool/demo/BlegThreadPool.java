package multithreading.demo.thread_pool.demo;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * 线程池管理
 * 
 * @author shiyuquan
 */
public class BlegThreadPool {
	/**
	 * corePoolSize ： 线程池维护线程的最少数量
	 * cpu密集型计算推荐设置线程池核心线程数为N，也就是和cpu的线程数相同，可以尽可能低避免线程间上下文切换。
	 * io密集型计算推荐设置线程池核心线程数为2N，但是这个数一般根据业务压测出来的，如果不涉及业务就使用推荐。
	 * 
	 * maximumPoolSize ：线程池维护线程的最大数量
	 * 
	 * keepAliveTime ： 线程池维护线程所允许的空闲时间
	 * 
	 * unit ： 线程池维护线程所允许的空闲时间的单位
	 * 
	 * workQueue ： 线程池所使用的缓冲队列
	 *
	 * 默认 返回jvm虚拟机可用的处理处理器数量
	 */
	private static int corePoolSize = Runtime.getRuntime().availableProcessors();
	private static int maximumPoolSize = Runtime.getRuntime().availableProcessors();

	/**
	 * 当线程池中的线程数大于corePoolSize时，keepAliveTime 为多余的空闲线程等待新任务的最长时间，
	 * 超过这个时间后多余的线程将被终止。这里把keepAliveTime设置为0L，意味着多余 的空闲线程会被立即终止。
	 */
	private static int keepAliveTime = 0;
	private static int queueSize = 100;
	private static BlegThreadPoolExecutor threadPool;

	// 先进先出阻塞队列
	private static final BlockingQueue<Runnable> workQueue = new BlegLinkedBlockingQueue<>(queueSize);

	private static BlegThreadPool instance = null;

	public static BlegThreadPool getInstance() {
		if (instance == null) {
			instance = new BlegThreadPool();
		}
		return instance;
	}

	public BlegThreadPool() {
		// 构造一个线程池
		threadPool = new BlegThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.SECONDS, workQueue,
				new NamedThreadFactory("blegThreadPool"), new BlegRejectedExecutionHandler());
	}

	public void shutdown() {
		threadPool.shutdownNow();
	}

	public void execute(Runnable a) {
		threadPool.execute(a);
	}
	//
	// public static void logPool() {
	// 	log.info("线程池 queue size：{}," +
	// 					 " core pool size: {}," +
	// 					 " max pool size: {}," +
	// 					 " pool size: {}," +
	// 					 " keepAlive: {}," +
	// 					 " 活动线程数: {}," +
	// 					 " task数量: {}," +
	// 					 " 线程池历史最大的线程数: {}," +
	// 					 " 总共执行的任务数: {}," +
	// 					 " ThreadFactory: {}," +
	// 					 " RejectedExecutionHandler: {}",
	// 			threadPool.getQueue().size(),
	// 			threadPool.getCorePoolSize(),
	// 			threadPool.getMaximumPoolSize(),
	// 			threadPool.getPoolSize(),
	// 			threadPool.getKeepAliveTime(TimeUnit.MILLISECONDS),
	// 			threadPool.getActiveCount(),
	// 			threadPool.getTaskCount(),
	// 			 threadPool.getLargestPoolSize(),
	// 			 threadPool.getCompletedTaskCount(),
	// 			 threadPool.getThreadFactory().getClass().getName(),
	// 			 threadPool.getRejectedExecutionHandler().getClass().getName()
	// 			 );
	// }
}
