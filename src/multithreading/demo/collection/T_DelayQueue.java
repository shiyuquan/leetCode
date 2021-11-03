package multithreading.demo.collection;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * 延迟队列
 *
 * @author shiyuquan
 * Create Time: 2019/7/16 10:00
 */
public class T_DelayQueue {

    static BlockingQueue<MyTask> tasks = new DelayQueue<>();

    static class MyTask implements Delayed {

        long runningTime;

        MyTask(long rt) {
            this.runningTime = rt;
        }

        @Override
        public long getDelay(TimeUnit unit) {
            return unit.convert(runningTime - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
        }

        @Override
        public int compareTo(Delayed o) {
            if (this.getDelay(TimeUnit.MILLISECONDS) < o.getDelay(TimeUnit.MILLISECONDS)) {
                return -1;
            } else if (this.getDelay(TimeUnit.MILLISECONDS) > o.getDelay(TimeUnit.MILLISECONDS)) {
                return 1;
            } else {
                return 0;
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        long now = System.currentTimeMillis();

        MyTask t1 = new MyTask(now + 1000);
        MyTask t2 = new MyTask(now + 2000);
        MyTask t3 = new MyTask(now + 1500);
        MyTask t4 = new MyTask(now + 2500);
        MyTask t5 = new MyTask(now + 500);

        System.out.println("1 -- " + t1);
        tasks.put(t1);
        System.out.println("2 -- " + t2);
        tasks.put(t2);
        System.out.println("3 -- " + t3);
        tasks.put(t3);
        System.out.println("4 -- " + t4);
        tasks.put(t4);
        System.out.println("5 -- " + t5);
        tasks.put(t5);

        System.out.println();
        System.out.println("延迟队列根据自己的排序规则，应该有的顺序是: 5-1-3-2-4");
        System.out.println();
        for (int i = 0; i < 5; i++) {
            System.out.println(tasks.take());
        }
    }
}
