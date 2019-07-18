package multithreading.collection;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * @author shiyuquan
 * Create Time: 2019/7/16 9:51
 */
public class T_ArrayBlockingQueue {

    static BlockingQueue<String> queue = new ArrayBlockingQueue<>(10);

    static Random r = new Random();

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            queue.put("a" + i);
        }
        queue.put("aaa");
    }
}
