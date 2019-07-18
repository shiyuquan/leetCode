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
 * @author shiyuquan
 * Create Time: 2019/7/15 10:52
 */
public class ReentrantLock2 {
    Lock lock = new ReentrantLock();

    public static void main(String[] args) {
        ReentrantLock2 reentrantLock2 = new ReentrantLock2();
        new Thread(reentrantLock2::m1).start();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(reentrantLock2::m2).start();
    }

    void m1() {
        try {

            lock.lock();
            for (int i = 0; i < 10; i++) {
                TimeUnit.SECONDS.sleep(1);
                System.out.println(i);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    /**
     * 可以通过tryLock来判断锁是否锁定
     * 也可以指定尝试的时间，由于tryLock(time)会抛出异常，所以要注意unlock的处理，必须放在finally中。
     */
    void m2() {
        // lock.lock();
        // System.out.println("m2 ...");
        // lock.unlock();

        boolean locked = false;

        try {
            // 尝试在5秒内上锁，5秒后，不管锁定了没有都执行后面的代码
            // 后面的代码可以通过锁定状态来做些事情
            locked = lock.tryLock(5, TimeUnit.SECONDS);
            System.out.println("m2 ... " + locked);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (locked) {
                lock.unlock();
            }
        }
    }
}
