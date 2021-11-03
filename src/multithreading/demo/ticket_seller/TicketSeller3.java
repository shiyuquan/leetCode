package multithreading.demo.ticket_seller;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 使用ConcurrentQueue提高并发性
 * @author shiyuquan
 * Create Time: 2019/7/15 19:43
 */
public class TicketSeller3 {

    static Queue<String> tickets = new ConcurrentLinkedQueue<>();


    static {
        for (int i = 0; i < 10000; i++) {
            tickets.add("ticket: " + i);
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                while (true) {
                    String s = tickets.poll();
                    if (s == null) {
                        break;
                    }
                    System.out.println("sell -- " + s);
                }
            }).start();
        }
    }
}
