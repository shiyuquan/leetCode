package multithreading.demo.ticket_seller;

import java.util.ArrayList;
import java.util.List;

/**
 * 就算操作A和B都是同步的，但A和B组成的复合操作也未必是同步的，仍然需要自己进行同步
 * 就像这个程序，判断size和进行remove必须是一整个的原子操作
 *
 * @author shiyuquan
 * Create Time: 2019/7/15 19:31
 */
public class TicketSeller2 {

    static List<String> tickets = new ArrayList<>();
    // static Vector<String> tickets = new Vector<>();

    static {
        for (int i = 0; i < 10000; i++) {
            tickets.add("ticket: " + i);
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                while (tickets.size() > 0) {
                    synchronized(tickets) {
                        if (tickets.size() == 0) {
                            break;
                        }
                        System.out.println("sell -- " + tickets.remove(0));
                    }
                }
            }).start();
        }
    }
}
