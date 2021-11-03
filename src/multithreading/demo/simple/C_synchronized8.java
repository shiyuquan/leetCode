package multithreading.demo.simple;

/**
 * 用字符串常量作为锁的时候，需要非常谨慎地处理
 * 下面例子中， m1,m2其实锁定的是同一个对象
 * 这种情况还会发生比较诡异的现象，比如你用到了一个类库，在该类库中代码锁定了字符串“Hello”，
 * 但是你读不到源码，所以你在自己的代码中也锁定了"Hello",这时候就有可能发生非常诡异的死锁阻塞，
 * 因为你的程序和你用到的类库不经意间使用了同一把锁
 *
 * @author shiyuquan
 * Create Time: 2019/7/4 11:05
 */
public class C_synchronized8 {

    String s1 = "hello";
    String s2 = "hello";

    void m() {
        synchronized (s1) {

        }
    }

    void m2() {
        synchronized (s2) {

        }
    }
}
