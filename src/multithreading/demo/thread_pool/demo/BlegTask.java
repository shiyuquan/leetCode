package multithreading.demo.thread_pool.demo;


/**
 * default task
 */
public class BlegTask implements Runnable {

    public BlegTask() {}

    @Override
    public void run() {
        System.err.println("BlegTask");
        // do some thing
    }
}
