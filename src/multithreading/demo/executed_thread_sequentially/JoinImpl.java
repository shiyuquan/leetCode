package multithreading.demo.executed_thread_sequentially;

/**
 * 利用 join 方法，进行插队
 *
 * @author shiyuquan
 * @since 2021/6/23 3:56 下午
 */
public class JoinImpl {

    public static void main(String[] args) {
        SynchronizedAndVolatileImpl saa = new SynchronizedAndVolatileImpl();

        try{
            A a = new A();
            B b = new B();
            C c = new C();
            Thread ta = new Thread(a);
            Thread tb = new Thread(b);
            Thread tc = new Thread(c);
            ta.start();

            ta.join();
            tb.start();

            tb.join();
            tc.start();
        } catch (Exception e) {

        }

    }

}