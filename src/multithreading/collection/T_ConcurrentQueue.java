package multithreading.collection;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 关于queue的存取，各有三个方法，详情见博客
 * https://blog.csdn.net/z69183787/article/details/46986823
 *
 * @author shiyuquan
 * Create Time: 2019/7/15 20:49
 */
public class T_ConcurrentQueue {
    public static void main(String[] args) {
        Queue<String> queue = new ConcurrentLinkedQueue<>();
        for (int i = 0; i < 10; i++) {
            queue.offer("a" + i);
        }

        System.out.println(queue);

        System.out.println(queue.size());

        System.out.println(queue.poll());
        System.out.println(queue.size());

        System.out.println(queue.peek());
        System.out.println(queue.size());
    }
}
