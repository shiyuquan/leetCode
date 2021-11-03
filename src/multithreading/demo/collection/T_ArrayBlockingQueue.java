package multithreading.demo.collection;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * 内部使用 {@link java.util.concurrent.locks.ReentrantLock}
 *
 * @author shiyuquan
 * Create Time: 2019/7/16 9:51
 */
public class T_ArrayBlockingQueue {

    static BlockingQueue<String> queue = new ArrayBlockingQueue<>(10);

    static Random r = new Random();

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            // put 会判断 队列是不是满了，满了会通过 condition 条件做 await 操作，将线程中断
            queue.put("a" + i);
        }
        // 这里线程中断了
        queue.put("aaa");
    }
}
