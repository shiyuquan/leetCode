package multithreading.thread_pool;

import java.util.concurrent.Executor;

/**
 * @author shiyuquan
 * Create Time: 2019/7/16 17:04
 */
public class T_MyExcutor implements Executor {

    public static void main(String[] args) {
        new T_MyExcutor().execute(() -> System.out.println("hello executor"));
    }

    @Override
    public void execute(Runnable command) {
        command.run();
    }
}
