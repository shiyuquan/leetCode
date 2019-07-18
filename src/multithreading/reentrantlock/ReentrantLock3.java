package multithreading.reentrantlock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * reentrantlock用于替代synchrnized
 * 使用reentrantlock可以完成synchronized同样的功能
 * 需要注意的是，必须要手动释放锁
 * 使用synchronized遇见异常，jvm会自动释放锁，但是reentrantlock必须手动释放锁，所以需要经常在finally中调用unlock()方法
 *
 * reentrantlock可以进行 尝试锁定 trylock，这样在无法锁定或者在指定时间内无法锁定，线程可以决定是否继续等待
 *
 * 使用reentrantlock可以调用lockInterruptibly方法，可以对线程的interrupt方法做出响应
 * 在一个线程等待过程中，可以被打断
 *
 * @author shiyuquan
 * Create Time: 2019/7/15 11:32
 */
public class ReentrantLock3 {

    public static void main(String[] args) {
        Lock lock = new ReentrantLock();

        Thread t1 = new Thread(() -> {
            try {
                lock.lock();
                System.out.println("t1 start");
                TimeUnit.SECONDS.sleep(Integer.MAX_VALUE);
                System.out.println("t1 end");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        });
        t1.start();

        Thread t2 = new Thread(() -> {
            try {
                // lock.lock();
                // lockInterruptibly方法，名称上我的理解是可以打断的加锁
                // 它会调用Thread.interrupted()方法判断当前线程是否中断，中断情况下抛出异常
                // 线程中断的信息参考下面的博客
                // https://blog.csdn.net/xinxiaoyong100440105/article/details/80931705
                lock.lockInterruptibly();
                System.out.println("t2 start");
                TimeUnit.SECONDS.sleep(5);
                System.out.println("t2 end");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        });
        t2.start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 打断线程2等待
        t2.interrupt();
    }
}
