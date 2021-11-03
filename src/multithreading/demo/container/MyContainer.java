package multithreading.demo.container;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 面试题
 * 实现一个容器，提供add，size方法
 * 写两个线程，线程1添加10个元素到容器中，线程2实现控制元素个数，当个数为5时，线程2给出提示并退出
 *
 * 分析下面的程序，能完成功能吗？
 *
 * 没有volatile不能实现功能，原因在于这里线程一修改内容线程二不知道，所以线程二不会结束。
 * 加上volatile通知所有线程资源变动
 *
 * @author shiyuquan
 * Create Time: 2019/7/5 10:17
 */
public class MyContainer {

    volatile List list = new ArrayList();

    public void add(Object o) {
        list.add(o);
    }

    public int size() {
        return list.size();
    }

    public static void main(String[] args) {
        MyContainer c = new MyContainer();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                c.add(new Object());
                System.out.println("add" + i);
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "t1").start();

        new Thread(() -> {
            while (true) {
                if (c.size() == 5) {
                    break;
                }
            }
            System.out.println("t2 end");
        }, "t2").start();
    }
}
