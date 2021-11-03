package multithreading.demo.producer_consumer;

import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

/**
 * 写一个固定的同步容器，拥有put，get方法，以及getCount方法
 * 能够支持两个生产者，10个消费者线程的阻塞调用
 *
 * 使用wait/notifyAll实现
 *
 * @author shiyuquan
 * Create Time: 2019/7/15 14:44
 */
public class MyContainer<T> {
    private final LinkedList<T> lists = new LinkedList<>();
    private final int MAX = 10;
    private int count = 0;

    public static void main(String[] args) {
        MyContainer<String> c = new MyContainer<>();
        // 定义10个消费者
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                for (int j = 0; j < 5; j++) {
                    System.out.println(Thread.currentThread().getName()+ " consume: " + c.get());
                }
            }, "consumer-" + i).start();
        }
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < 2; i++) {
            new Thread(() -> {
                for (int j = 0; j < 25; j++) {
                    c.put(Thread.currentThread().getName() + " produce: " + j);
                }
            }, "producer-" + i).start();
        }
    }

    public synchronized void put(T t) {
        // 容器满了，等待消费者消费
        while(lists.size() == MAX) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        lists.add(t);
        ++count;
        this.notifyAll();
    }

    public synchronized T get() {
        T t = null;
        // 容器内没有东西，等待生产者生产
        while (lists.size() == 0) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        t = lists.removeFirst();
        count--;
        this.notifyAll();
        return t;
    }
}
