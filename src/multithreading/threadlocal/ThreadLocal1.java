package multithreading.threadlocal;

import java.util.concurrent.TimeUnit;

/**
 * 线程局部变量
 *
 * @author shiyuquan
 * Create Time: 2019/7/15 15:26
 */
public class ThreadLocal1 {
    volatile static Persion p = new Persion();

    public static void main(String[] args) {
        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(p.name);
        }).start();

        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            p.name = "SYQ";
        }).start();
    }
}

class Persion {
    String name = "syq";
}
