package multithreading.demo.collection;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;

/**
 * @author shiyuquan
 * Create Time: 2019/7/16 13:10
 */
public class T_SynchronusQueue {

    public static void main(String[] args) throws InterruptedException {
        // 容量为0
        BlockingQueue<String> strs = new SynchronousQueue<>();
        new Thread(()->{
            try {
                System.out.println(strs.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        //阻塞等待消费者消费
        strs.put("aaa");
        //strs.add("aaa");
        System.out.println(strs.size());
    }
}
