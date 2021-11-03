package multithreading.demo.simple;

import java.util.concurrent.TimeUnit;

/**
 * 对业务代码写方法加锁，不对读方法加锁容易产生脏读问题（diretyRead）
 * @author shiyuquan
 * Create Time: 2019/7/2 15:10
 */
public class Account {
    String name;
    double balance;

    public synchronized double get(String name) {
        return this.balance;
    }

    public synchronized void set(String name, double balance) {
        this.name = name;
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.balance = balance;
    }

    public static void main(String[] args) {
        Account account = new Account();
        new Thread(() -> account.set("shi", 100.0)).start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(account.get("shi"));

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(account.get("shi"));
    }
}
