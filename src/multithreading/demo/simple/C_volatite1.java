package multithreading.demo.simple;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * volatile不能保证多个线程同时修改变量时锁带来的不一致问题，它不具备原子性，volatile不能替代synchronized
 *
 * 将synchronized标记方法m()，取代volatile标识count。结果可以得到保证。
 * @author shiyuquan
 * Create Time: 2019/7/4 9:18
 */
public class C_volatite1 {
    /*volatile*/ int count = 0;

    synchronized void m() {
        for (int i = 0; i < 10000; i++) {
            count++;
        }
    }

    /**
     * 解决同样问题更高效的方法，使用AtomXXX类
     * AtomXXX类本身方法都是原子性的，但不能保证多个方法连续调用是原子性的
     */
    AtomicInteger c = new AtomicInteger(0);
    void m2() {
        for (int i = 0; i < 10000; i++) {
            // c++
            c.incrementAndGet();
        }
    }

    public static void main(String[] args) {
        C_volatite1 c = new C_volatite1();
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            threads.add(new Thread(() -> c.m2(), "t-" + i));
        }

        threads.forEach((o) -> o.start());

        threads.forEach((o) -> {
            try {
                // join()方法的解释 https://www.jianshu.com/p/fc51be7e5bc0
                o.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        System.out.println(c.c);
    }
}
