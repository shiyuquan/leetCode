package multithreading.threadlocal;

import java.util.concurrent.TimeUnit;

/**
 * ThreadLocal 线程局部变量，其存放的内容只有在本身线程才能用
 * @author shiyuquan
 * Create Time: 2019/7/15 15:33
 */
public class ThreadLocal2 {

    static ThreadLocal<String> p = new ThreadLocal();

    public static void main(String[] args) {
        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            p.set("ddd");
            System.out.println("t1 - " + p.get());
        }).start();

        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            p.set("sss");
            System.out.println("t2 - " + p.get());
        }).start();
    }
}
