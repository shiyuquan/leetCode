package multithreading.demo.executed_thread_sequentially;

/**
 * @author shiyuquan
 * @since 2021/6/24 10:11 上午
 */
public class C implements Runnable {

    /**
     * When an object implementing interface <code>Runnable</code> is used to create a thread, starting the thread
     * causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        System.err.println("C");
    }
}
