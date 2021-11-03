package multithreading.demo.executed_thread_sequentially;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 使用Lock 的Condition(条件变量)方法 通常与一个锁关联。需要在多个Contidion中共享一个锁时，可以传递一个Lock/RLock实例给构造方法，否则它将自己生成一个RLock实例。
 * <p>
 * Condition中await() 方法类似于Object类中的wait()方法。 Condition中await(long time,TimeUnit unit) 方法类似于Object类中的wait(long time)方法。
 * Condition中signal() 方法类似于Object类中的notify()方法。 Condition中signalAll() 方法类似于Object类中的notifyAll()方法。
 *
 * @author shiyuquan
 * @since 2021/6/24 10:16 上午
 */
public class LockConditionImpl {

    private static volatile int nextPrintWho = 1;

    private static final ReentrantLock LOCK = new ReentrantLock();

    private static final Condition conditionA = LOCK.newCondition();

    public static void main(String[] args) {

        Thread threadA = new Thread(() -> {
            try {
                LOCK.lock();
                System.out.println("ThreadA:");
                nextPrintWho = 2;
                conditionA.signalAll();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                LOCK.unlock();
            }
        });

        Thread threadB = new Thread(() -> {
            try {
                LOCK.lock();
                while (nextPrintWho != 2) {
                    conditionA.await();
                }

                System.out.println("ThreadB:");
                nextPrintWho = 3;
                conditionA.signal();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                LOCK.unlock();
            }
        });

        Thread threadC = new Thread(() -> {
            try {
                LOCK.lock();
                while (nextPrintWho != 3) {
                    conditionA.await();
                }
                System.out.println("ThreadC:");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                LOCK.unlock();
            }
        });

        threadA.start();
        threadB.start();
        threadC.start();

    }
}
