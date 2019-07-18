package multithreading.ticket_seller;

import java.util.Vector;

/**
 * 有n张车票，每张票都有一个编号
 * 同时有10个窗口对外售票
 * 写一个模拟程序
 *
 * 下面的程序有啥问题？
 * 使用vector或者Collections.synchronizedxxx能解决问题吗？
 *
 * @author shiyuquan
 * Create Time: 2019/7/15 16:46
 */
public class TicketSeller1 {

    // static List<String> tickets = new ArrayList<>();
    static Vector<String> tickets = new Vector<>();

    static {
        for (int i = 0; i < 10000; i++) {
            tickets.add("ticket: " + i);
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                while (tickets.size() > 0) {
                    System.out.println("sell -- " + tickets.remove(0));
                }
            }).start();
        }
    }
}
