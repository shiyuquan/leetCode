package multithreading;

import java.util.concurrent.TimeUnit;

/**
 * @author shiyuquan
 * Create Time: 2019/7/4 10:58
 */
public class C_synchronized7 {
    Object o = new Object();
     void m() {
         synchronized (o) {
             while (true) {
                 try {
                     TimeUnit.SECONDS.sleep(1);
                 } catch (InterruptedException e) {
                     e.printStackTrace();
                 }
                 System.out.println(Thread.currentThread().getName());
             }
         }
     }

    public static void main(String[] args) {
        C_synchronized7 c = new C_synchronized7();
        new Thread(() -> c.m()).start();

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Thread t2 = new Thread(() -> c.m(), "t2");
        // 锁对象发生了改变，所以t2得以执行，如果注释掉这句话，t2将永远得不到运行
        c.o = new Object();
        t2.start();
    }
}
