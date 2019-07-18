package multithreading.reentrantlock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author shiyuquan
 * Create Time: 2019/7/15 10:22
 */
public class ReentrantLock1 {
    public static void main(String[] args) {
        Counter counter = new Counter();

        Runnable r = new Runnable() {
            @Override
            public void run() {
                while (counter.getCount() >= 0) {
                    counter.desc();
                }
            }
        };

        new Thread(r).start();
        new Thread(r).start();
    }
}

class Counter {
    private int count = 100;

    private Lock lock = new ReentrantLock(true);

    // public synchronized void desc() {
    //     System.out.println(Thread.currentThread().getName() + "--->" + count);
    //     count--;
    // }

    public void desc() {
        lock.lock();
        // try {
        //     TimeUnit.SECONDS.sleep(1);
        // } catch (InterruptedException e) {
        //     e.printStackTrace();
        // }
        System.out.println(Thread.currentThread().getName() + "--->" + count);
        count--;
        lock.unlock();
    }

    public int getCount() {
        return count;
    }
}
