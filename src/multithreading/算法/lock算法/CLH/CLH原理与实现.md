# 前言：什么是自旋锁和互斥锁

因为CLH是一种自旋锁，所以先讲一下什么是自旋锁以及和互斥锁的关系

## 什么是互斥锁

在多个线程竞争锁的时候，只有一个线程能获取锁，没能抢到锁的线程会进入`休眠状态（sleep）`，当锁被释放的时候休眠的线程再次尝试获取锁。

缺点是睡眠唤醒的一些操作需要切换线程，有很多cpu指令，也会很消耗时间，如果cpu执行线程切换时间比锁占用时间久，就不如自旋锁效率高。

> 所以互斥锁适用于锁占用时间长的场景。

## 什么是自旋锁

自旋锁是`互斥锁`的一种，特点是没能抢到锁的时候会自旋，处于`忙碌的等待状态（busy-waiting）`而并不是`休眠（sleep）`，会一直使用cpu。

> 因为自旋会一直消耗cpu，所以适用于上锁时间短的场合。

# CLH锁

`CLH锁`是一种 `基于逻辑队列` 的 `非线程饥饿` 的 `自旋` 的 `公平锁`，由Craig、Landin、Hagersten 三位一起发明的，所以叫CLH锁。

> 个人认为，这里的非饥饿是设计概念上的，实际使用不当可能还是会造成饥饿的现象。

## 原理

1. 需要一个尾节点，通过尾节点指针来构建线程的逻辑队列，做到线程先到先得的服务公平性。需要保证尾节点的原子性，避免多线程下线程安全问题。
2. 需要一个自旋变量，线程会通过通过上一个线程的变量状态来判断自己是否执行。因为每个线程的入队都是通过尾节点来获取前一个线程的变量，而尾节是原子性的，，所以保证了该变量的线程安全性。

## java 源码实现

``` java
import java.util.concurrent.atomic.AtomicReference;

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

```

运行结果

```
...
线程 Thread-97 获得锁！！！！！！
线程 Thread-97 释放了锁！！！！！！
线程 Thread-99 未获得锁。。。。。。
线程 Thread-99 未获得锁。。。。。。
线程 Thread-99 未获得锁。。。。。。
线程 Thread-99 未获得锁。。。。。。
线程 Thread-99 未获得锁。。。。。。
线程 Thread-98 未获得锁。。。。。。
线程 Thread-98 获得锁！！！！！！
线程 Thread-98 释放了锁！！！！！！
线程 Thread-99 未获得锁。。。。。。
线程 Thread-99 获得锁！！！！！！
线程 Thread-99 释放了锁！！！！！！
--------->100

```

