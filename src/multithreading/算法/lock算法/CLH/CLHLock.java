package multithreading.算法.lock算法.CLH;

import java.util.concurrent.atomic.AtomicReference;

/**
 * CLH lock demo
 *
 * `CLH锁`是一种 `基于逻辑队列` 的 `非线程饥饿` 的 `自旋` 的 `公平锁`，由Craig、Landin、Hagersten 三位一起发明的，所以叫CLH锁。
 *
 * juc 的核心类 AbstractQueuedSynchronizer 基于该算法做的修改
 *
 * 基本概念
 * 1. 需要一个尾节点，通过尾节点指针来构建线程的逻辑队列，做到线程先到先得的服务公平性。需要保证尾节点的原子性，避免多线程下线程安全问题。
 *
 * 2. 需要一个自旋变量，线程会通过通过上一个线程的变量状态来判断自己是否执行。因为每个线程的入队都是通过尾节点来获取前一个线程的变量，而尾节是
 *    原子性的，，所以保证了该变量的线程安全性。
 *
 * @author shiyuquan
 * @since 2021/10/27 3:09 下午
 */
public class CLHLock {

    /**
     * 基础单位节点，这里只设置做一个节点状态
     */
    private static class Node {

        /**
         * 锁状态，默认 false 表示没有线程活动锁
         * true 表示线程获得了锁，或者还在等待锁
         * 为了保证该状态线程间可见，使用 volatile 关键字
         */
        volatile boolean lockStatus = false;

    }

    /**
     * 尾节点，总是指向最后一个线程的 curNode
     * 使用java自带的原子类 Atomic 保证原子性
     */
    private final AtomicReference<Node> tailNode;

    /**
     * 前置节点，指向上一个线程的 curNode
     */
    private final ThreadLocal<Node> preNode;

    /**
     * 当前节点
     */
    private final ThreadLocal<Node> curNode;

    public CLHLock() {
        // 创建一个尾节点
        tailNode = new AtomicReference<>(new Node());
        // 初始化 curNode
        curNode = ThreadLocal.withInitial(() -> new Node());
        // 初始化 preNode，为空
        preNode = new ThreadLocal<>();
    }

    /**
     * 加锁
     */
    public void lock() {
        // 获取当前线程的节点
        Node cn = curNode.get();

        // 设置当前线程的状态为 true 表示获得了锁，或者在等待锁
        cn.lockStatus = true;

        // 将原来的尾节点取出，即获得上一个线程的当前节点
        // 然后指向当前线程的当前节点
        Node pn = tailNode.getAndSet(cn);

        // 将上一个线程的当前节点的设置为该线程的前置节点
        preNode.set(pn);

        // 自旋等待前置节点的状态
        while (pn.lockStatus) {
            System.err.println("线程 " + Thread.currentThread().getName() + " 未获得锁。。。。。。");
        }
        System.err.println("线程 " + Thread.currentThread().getName() + " 获得锁！！！！！！");
    }

    /**
     * 释放锁
     */
    public void unLock() {

        // 获取当前线程节点
        Node cn = curNode.get();

        // 将状态设置为 false 表示释放了锁
        cn.lockStatus = false;

        System.err.println("线程 " + Thread.currentThread().getName() + " 释放了锁！！！！！！");

        // 将该线程的当前节点重制，防止一个线程 获得锁 - 释放锁 - 再获得锁 造成的死锁情况。
        Node newCurrNode = new Node();
        curNode.set(newCurrNode);
    }


    static int a = 0;

    public static void main(String[] args) throws Exception {
        final CLHLock lock = new CLHLock();
        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                lock.lock();
                a ++;
                lock.unLock();
            }).start();
        }
        Thread.sleep(5000);
        System.err.println("--------->" + a);
    }
}
