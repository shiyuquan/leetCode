package multithreading.demo.collection;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 *
 * 写时复制容器 copy on write
 * 多线程下，写时效率低，读时效率高
 * 适合写少读多的场景
 *
 * @author shiyuquan
 * Create Time: 2019/7/15 20:31
 */
public class T_CopyOnWriteList {
    public static void main(String[] args) {
        List<String> lists = new CopyOnWriteArrayList<>();
        Random r = new Random();
        Thread[] ths = new Thread[100];
        for (int i = 0; i < ths.length; i++) {
            Runnable task = new Runnable() {
                @Override
                public void run() {
                    for (int j = 0; j < 1000; j++) {
                        lists.add("a" + r.nextInt(10000));
                    }
                }
            };
            ths[i] = new Thread(task);
        }

        runAndComputeTime(ths);
    }

    private static void runAndComputeTime(Thread[] ths) {
        long s1 = System.currentTimeMillis();

        Arrays.asList(ths).forEach(t -> t.start());
        Arrays.asList(ths).forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        long s2 = System.currentTimeMillis();
        System.out.println(s2 - s1);
    }
}
