package multithreading.demo.thread_pool.demo;

import java.io.Serializable;
import java.util.AbstractQueue;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;



/**
 * An optionally-bounded {@linkplain BlockingQueue blocking queue} based on
 * linked nodes.
 * This queue orders elements FIFO (first-in-first-out).
 * The <em>head</em> of the queue is that element that has been on the
 * queue the longest time.
 * The <em>tail</em> of the queue is that element that has been on the
 * queue the shortest time. New elements
 * are inserted at the tail of the queue, and the queue retrieval
 * operations obtain elements at the head of the queue.
 * Linked queues typically have higher throughput than array-based queues but
 * less predictable performance in most concurrent applications.
 *
 * <p>The optional capacity bound constructor argument serves as a
 * way to prevent excessive queue expansion. The capacity, if unspecified,
 * is equal to {@link Integer#MAX_VALUE}.  Linked nodes are
 * dynamically created upon each insertion unless this would bring the
 * queue above capacity.
 *
 * <p>This class and its iterator implement all of the
 * <em>optional</em> methods of the {@link Collection} and {@link
 * Iterator} interfaces.
 *
 * <p>This class is a member of the
 * <a href="{@docRoot}/../technotes/guides/collections/index.html">
 * Java Collections Framework</a>.
 *
 * @since 1.5
 * @author Doug Lea
 * @param <E> the type of elements held in this collection
 */

/**
 * 可修改容量的阻塞队列
 *
 * juc 里面很多 类似于
 *
 * private final AtomicInteger count = new AtomicInteger(0);
 * ...
 * method() {
 *     final AtomicInteger count = this.count;
 * }
 *
 * 这样的写法
 * 原因：
 * https://stackoverflow.com/questions/13155860/why-does-jdk-sourcecode-take-a-final-copy-of-volatile-instances
 *
 * 大致解释：
 * 就是cpu缓存获取读取栈内存 比 随机堆快。这个写法属于对cpu的优化。
 * 就算不这样写，JIT 也有负责代码优化，而 Doug Lea 基于原则性，选择通过代码实现
 *
 * @author shiyuquan
 * @since 2021/11/1 10:29 上午
 */
public class BlegLinkedBlockingQueue<E> extends AbstractQueue<E>
        implements BlockingQueue<E>, Serializable {

    /*
     * A variant of the "two lock queue" algorithm.  The putLock gates
     * entry to put (and offer), and has an associated condition for
     * waiting puts.  Similarly for the takeLock.  The "count" field
     * that they both rely on is maintained as an atomic to avoid
     * needing to get both locks in most cases. Also, to minimize need
     * for puts to get takeLock and vice-versa, cascading notifies are
     * used. When a put notices that it has enabled at least one take,
     * it signals taker. That taker in turn signals others if more
     * items have been entered since the signal. And symmetrically for
     * takes signalling puts. Operations such as remove(Object) and
     * iterators acquire both locks.
     *
     * Visibility between writers and readers is provided as follows:
     *
     * Whenever an element is enqueued, the putLock is acquired and
     * count updated.  A subsequent reader guarantees visibility to the
     * enqueued Node by either acquiring the putLock (via fullyLock)
     * or by acquiring the takeLock, and then reading n = count.get();
     * this gives visibility to the first n items.
     *
     * To implement weakly consistent iterators, it appears we need to
     * keep all Nodes GC-reachable from a predecessor dequeued Node.
     * That would cause two problems:
     * - allow a rogue Iterator to cause unbounded memory retention
     * - cause cross-generational linking of old Nodes to new Nodes if
     *   a Node was tenured while live, which generational GCs have a
     *   hard time dealing with, causing repeated major collections.
     * However, only non-deleted Nodes need to be reachable from
     * dequeued Nodes, and reachability does not necessarily have to
     * be of the kind understood by the GC.  We use the trick of
     * linking a Node that has just been dequeued to itself.  Such a
     * self-link implicitly means to advance to head.next.
     */

    /**
     * Linked list node class
     */
    static class Node<E> {
        /** The item, volatile to ensure barrier separating write and read */
        // TODO 变动 添加了 volatile 意义在哪
        volatile E item;
        Node<E> next;
        Node(E x) { item = x; }
    }

    /**
     * 去除 final 关键字 让队列可变大小
     * The capacity bound, or Integer.MAX_VALUE if none
     */
    private int capacity;

    /** Current number of elements */
    private final AtomicInteger count = new AtomicInteger(0);

    /** Head of linked list */
    private transient Node<E> head;

    /** Tail of linked list */
    private transient Node<E> last;

    /**
     *  Lock held by take, poll, etc
     *  获取元素的锁
     */
    private final ReentrantLock takeLock = new ReentrantLock();

    /**
     * Wait queue for waiting takes
     * 判断是否在获取元素的标识
     */
    private final Condition notEmpty = takeLock.newCondition();

    /**
     *  Lock held by put, offer, etc
     *  添加元素的锁
     */
    private final ReentrantLock putLock = new ReentrantLock();

    /**
     *  Wait queue for waiting puts
     *  判断是否在添加元素的标识
     */
    private final Condition notFull = putLock.newCondition();

    /**
     * 唤醒一个等待获取的线程
     * Signal a waiting take. Called only from put/offer (which do not
     * otherwise ordinarily lock takeLock.)
     */
    private void signalNotEmpty() {
        final ReentrantLock takeLock = this.takeLock;
        takeLock.lock();
        try {
            notEmpty.signal();
        } finally {
            takeLock.unlock();
        }
    }

    /**
     * 唤醒一个等待添加的线程
     * Signal a waiting put. Called only from take/poll.
     */
    private void signalNotFull() {
        final ReentrantLock putLock = this.putLock;
        putLock.lock();
        try {
            notFull.signal();
        } finally {
            putLock.unlock();
        }
    }

    /**
     * 原方法： enqueue(Node<E> node)
     * Create a node and link it at end of queue
     * @param x the item
     */
    private void insert(E x) {
        last = last.next = new Node<E>(x);
    }

    /**
     * 原方法： dequeue()
     * Remove a node from head of queue,
     * @return the node
     */
    private E extract() {
        Node<E> h = head;
        Node<E> first = h.next;
        h.next = h; // help GC
        // Node<E> first = head.next;
        head = first;
        E x = first.item;
        first.item = null;
        return x;
    }

    /**
     * 变动 添加了 private 关键字
     * Lock to prevent both puts and takes.
     */
    private void fullyLock() {
        putLock.lock();
        takeLock.lock();
    }

    /**
     * 变动 添加了 private 关键字
     * Unlock to allow both puts and takes.
     */
    private void fullyUnlock() {
        takeLock.unlock();
        putLock.unlock();
    }


    /**
     * Creates a {@code LinkedBlockingQueue} with a capacity of
     * {@link Integer#MAX_VALUE}.
     */
    public BlegLinkedBlockingQueue() {
        this(Integer.MAX_VALUE);
    }

    /**
     * Creates a {@code LinkedBlockingQueue} with the given (fixed) capacity.
     *
     * @param capacity the capacity of this queue.
     * @throws IllegalArgumentException if {@code capacity} is not greater
     *         than zero.
     */
    public BlegLinkedBlockingQueue(int capacity) {
        if (capacity <= 0) throw new IllegalArgumentException();
        this.capacity = capacity;
        last = head = new Node<E>(null);
    }

    /**
     * Creates a {@code LinkedBlockingQueue} with a capacity of
     * {@link Integer#MAX_VALUE}, initially containing the elements of the
     * given collection,
     * added in traversal order of the collection's iterator.
     *
     * @param c the collection of elements to initially contain
     * @throws NullPointerException if {@code c} or any element within it
     * is {@code null}
     */
    public BlegLinkedBlockingQueue(Collection<? extends E> c) {
        // 原来直接 Integer.MAX_VALUE，这里简单修改了一下，直接乘以二不是什么好办法，需要按具体分析
        this(c.size() * 2);
        // 原来的方法里面，因为队列大小不能变，所以在这个方法里面使用元素的添加锁 putLock
        // 在锁范围内添加元素，判断元素是否为空，判断队列是否已满，并且用的 enqueue() 方法
        // 将每个元素直接放到队尾
        // 这里因为能修改容量，如果这里直接上 添加锁 那么其他线程进行 扩容、添加元素就可能会
        // 出问题，选择迭代地去 调用 add() 方法
        for (Iterator<? extends E> it = c.iterator(); it.hasNext();)
            add(it.next());
    }


    // this doc comment is overridden to remove the reference to collections
    // greater in size than Integer.MAX_VALUE
    /**
     * Returns the number of elements in this queue.
     *
     * @return  the number of elements in this queue.
     */
    @Override
    public int size() {
        return count.get();
    }

    /**
     * 新增的方法，修改队列的容量
     * 缩容多时候，原队列内容还在，如果原队列存量大于新设置的大小，将会添加不进去。
     *
     * Set a new capacity for the queue. Increasing the capacity can
     * cause any waiting {@link #put(Object)} invocations to succeed if the new
     * capacity is larger than the queue.
     * @param capacity the new capacity for the queue
     */
    public void setCapacity(int capacity) {
        final int oldCapacity = this.capacity;
        this.capacity = capacity;
        final int size = count.get();
        if (capacity > size && size >= oldCapacity) {
            signalNotFull();
        }
    }

    // this doc comment is a modified copy of the inherited doc comment,
    // without the reference to unlimited queues.
    /**
     * Returns the number of elements that this queue can ideally (in
     * the absence of memory or resource constraints) accept without
     * blocking. This is always equal to the initial capacity of this queue
     * less the current {@code size} of this queue.
     *
     * <p>Note that you <em>cannot</em> always tell if
     * an attempt to {@code add} an element will succeed by
     * inspecting {@code remainingCapacity} because it may be the
     * case that a waiting consumer is ready to {@code take} an
     * element out of an otherwise full queue.
     */
    @Override
    public int remainingCapacity() {
        return capacity - count.get();
    }

    /**
     * Adds the specified element to the tail of this queue, waiting if
     * necessary for space to become available.
     *
     * @param o the element to add
     * @throws InterruptedException if interrupted while waiting.
     * @throws NullPointerException if the specified element is {@code null}.
     */
    @Override
    public void put(E o) throws InterruptedException {
        if (o == null) throw new NullPointerException();
        // Note: convention in all put/take/etc is to preset
        // local var holding count  negative to indicate failure unless set.
        int c = -1;
        final ReentrantLock putLock = this.putLock;
        final AtomicInteger count = this.count;
        putLock.lockInterruptibly();
        try {
            /*
             * Note that count is used in wait guard even though it is
             * not protected by lock. This works because count can
             * only decrease at this point (all other puts are shut
             * out by lock), and we (or some other waiting put) are
             * signalled if it ever changes from
             * capacity. Similarly for all other uses of count in
             * other wait guards.
             */
            try {
                // 原来的判断是 count.get() == capacity
                // 原来的队列不能扩容，所以 == 判断，已有的队列大小 == 最大支持的容量 的话就不能添加元素了
                // 这里队列能扩容 所以 已有的队列大小 不大于 最大支持的容量 就行
                while (count.get() >= capacity)
                    notFull.await();
            } catch (InterruptedException ie) {
                notFull.signal(); // propagate to a non-interrupted thread
                throw ie;
            }
            insert(o);
            c = count.getAndIncrement();
            if (c + 1 < capacity)
                notFull.signal();
        } finally {
            putLock.unlock();
        }
        if (c == 0)
            signalNotEmpty();
    }

    /**
     * Inserts the specified element at the tail of this queue, waiting if
     * necessary up to the specified wait time for space to become available.
     *
     * @param o the element to add
     * @param timeout how long to wait before giving up, in units of {@code unit}
     * @param unit a {@code TimeUnit} determining how to interpret the {@code timeout} parameter
     * @return {@code true} if successful, or {@code false} if
     * the specified waiting time elapses before space is available.
     * @throws InterruptedException if interrupted while waiting.
     * @throws NullPointerException if the specified element is {@code null}.
     */
    @Override
    public boolean offer(E o, long timeout, TimeUnit unit)
            throws InterruptedException {

        if (o == null) throw new NullPointerException();
        long nanos = unit.toNanos(timeout);
        int c = -1;
        final ReentrantLock putLock = this.putLock;
        final AtomicInteger count = this.count;
        putLock.lockInterruptibly();
        try {
            // 自旋等待
            for (;;) {
                if (count.get() < capacity) {
                    insert(o);
                    c = count.getAndIncrement();
                    if (c + 1 < capacity)
                        notFull.signal();
                    break;
                }
                // count.get() >= capacity 的情况
                // 超时 添加失败
                if (nanos <= 0)
                    return false;
                try {
                    nanos = notFull.awaitNanos(nanos);
                } catch (InterruptedException ie) {
                    notFull.signal(); // propagate to a non-interrupted thread
                    throw ie;
                }
            }
        } finally {
            putLock.unlock();
        }
        if (c == 0)
            signalNotEmpty();
        return true;
    }

    /**
     * Inserts the specified element at the tail of this queue if possible,
     * returning immediately if this queue is full.
     *
     * @param o the element to add.
     * @return {@code true} if it was possible to add the element to
     *         this queue, else {@code false}
     * @throws NullPointerException if the specified element is {@code null}
     */
    @Override
    public boolean offer(E o) {
        if (o == null) throw new NullPointerException();
        final AtomicInteger count = this.count;
        // 原来的判断是 count.get() == capacity
        // 原来的队列不能扩容，所以 == 判断，已有的队列大小 == 最大支持的容量 的话就不能添加元素了
        // 这里队列能扩容 所以 已有的队列大小 不大于 最大支持的容量 就行
        if (count.get() >= capacity)
            return false;
        int c = -1;
        final ReentrantLock putLock = this.putLock;
        putLock.lock();
        try {
            if (count.get() < capacity) {
                insert(o);
                c = count.getAndIncrement();
                if (c + 1 < capacity)
                    notFull.signal();
            }
        } finally {
            putLock.unlock();
        }
        if (c == 0)
            signalNotEmpty();
        return c >= 0;
    }

    /**
     * 获取元素，没拿到 阻断线程
     */
    @Override
    public E take() throws InterruptedException {
        E x;
        int c = -1;
        final AtomicInteger count = this.count;
        final ReentrantLock takeLock = this.takeLock;
        takeLock.lockInterruptibly();
        try {
            try {
                while (count.get() == 0)
                    notEmpty.await();
            } catch (InterruptedException ie) {
                notEmpty.signal(); // propagate to a non-interrupted thread
                throw ie;
            }

            x = extract();
            c = count.getAndDecrement();
            if (c > 1)
                notEmpty.signal();
        } finally {
            takeLock.unlock();
        }
        // TODO 原来是 == 没懂这里的判读是做什么用的...
        if (c >= capacity)
            signalNotFull();
        return x;
    }

    /**
     * 获取元素，没拿到 返回null
     */
    @Override
    public E poll(long timeout, TimeUnit unit) throws InterruptedException {
        E x = null;
        int c = -1;
        long nanos = unit.toNanos(timeout);
        final AtomicInteger count = this.count;
        final ReentrantLock takeLock = this.takeLock;
        takeLock.lockInterruptibly();
        try {
            // 自旋
            for (;;) {
                if (count.get() > 0) {
                    x = extract();
                    c = count.getAndDecrement();
                    if (c > 1)
                        notEmpty.signal();
                    break;
                }
                if (nanos <= 0)
                    return null;
                try {
                    nanos = notEmpty.awaitNanos(nanos);
                } catch (InterruptedException ie) {
                    notEmpty.signal(); // propagate to a non-interrupted thread
                    throw ie;
                }
            }
        } finally {
            takeLock.unlock();
        }
        if (c >= capacity)
            signalNotFull();
        return x;
    }

    /**
     * 获取元素，没拿到 返回null
     */
    @Override
    public E poll() {
        final AtomicInteger count = this.count;
        if (count.get() == 0)
            return null;
        E x = null;
        int c = -1;
        final ReentrantLock takeLock = this.takeLock;
        takeLock.lock();
        try {
            if (count.get() > 0) {
                x = extract();
                c = count.getAndDecrement();
                if (c > 1)
                    notEmpty.signal();
            }
        } finally {
            takeLock.unlock();
        }
        // TODO 变更 原来的是 == 为什么有这个判断呢？
        if (c >= capacity)
            signalNotFull();
        return x;
    }

    /**
     * 返回队列头部元素 没有返回 {@code null}
     */
    @Override
    public E peek() {
        if (count.get() == 0)
            return null;
        final ReentrantLock takeLock = this.takeLock;
        takeLock.lock();
        try {
            Node<E> first = head.next;
            if (first == null)
                return null;
            else
                return first.item;
        } finally {
            takeLock.unlock();
        }
    }

    /**
     * 移除指定参数并返回
     */
    @Override
    public boolean remove(Object o) {
        if (o == null) return false;
        boolean removed = false;
        fullyLock();
        try {
            Node<E> trail = head;
            Node<E> p = head.next;
            while (p != null) {
                if (o.equals(p.item)) {
                    removed = true;
                    break;
                }
                trail = p;
                p = p.next;
            }
            if (removed) {
                p.item = null;
                trail.next = p.next;
                if (count.getAndDecrement() >= capacity)
                    notFull.signalAll();
            }
        } finally {
            fullyUnlock();
        }
        return removed;
    }

    /**
     * Returns {@code true} if this queue contains the specified element.
     * More formally, returns {@code true} if and only if this queue contains
     * at least one element {@code e} such that {@code o.equals(e)}.
     *
     * @param o object to be checked for containment in this queue
     * @return {@code true} if this queue contains the specified element
     */
    public boolean contains(Object o) {
        if (o == null) return false;
        fullyLock();
        try {
            for (Node<E> p = head.next; p != null; p = p.next)
                if (o.equals(p.item))
                    return true;
            return false;
        } finally {
            fullyUnlock();
        }
    }

    /**
     * Returns an array containing all of the elements in this queue, in
     * proper sequence.
     *
     * <p>The returned array will be "safe" in that no references to it are
     * maintained by this queue.  (In other words, this method must allocate
     * a new array).  The caller is thus free to modify the returned array.
     *
     * <p>This method acts as bridge between array-based and collection-based
     * APIs.
     *
     * @return an array containing all of the elements in this queue
     */
    @Override
    public Object[] toArray() {
        fullyLock();
        try {
            int size = count.get();
            Object[] a = new Object[size];
            int k = 0;
            for (Node<E> p = head.next; p != null; p = p.next)
                a[k++] = p.item;
            return a;
        } finally {
            fullyUnlock();
        }
    }

    /**
     * Returns an array containing all of the elements in this queue, in
     * proper sequence; the runtime type of the returned array is that of
     * the specified array.  If the queue fits in the specified array, it
     * is returned therein.  Otherwise, a new array is allocated with the
     * runtime type of the specified array and the size of this queue.
     *
     * <p>If this queue fits in the specified array with room to spare
     * (i.e., the array has more elements than this queue), the element in
     * the array immediately following the end of the queue is set to
     * {@code null}.
     *
     * <p>Like the {@link #toArray()} method, this method acts as bridge between
     * array-based and collection-based APIs.  Further, this method allows
     * precise control over the runtime type of the output array, and may,
     * under certain circumstances, be used to save allocation costs.
     *
     * <p>Suppose {@code x} is a queue known to contain only strings.
     * The following code can be used to dump the queue into a newly
     * allocated array of {@code String}:
     *
     *  <pre> {@code String[] y = x.toArray(new String[0]);}</pre>
     *
     * Note that {@code toArray(new Object[0])} is identical in function to
     * {@code toArray()}.
     *
     * @param a the array into which the elements of the queue are to
     *          be stored, if it is big enough; otherwise, a new array of the
     *          same runtime type is allocated for this purpose
     * @return an array containing all of the elements in this queue
     * @throws ArrayStoreException if the runtime type of the specified array
     *         is not a supertype of the runtime type of every element in
     *         this queue
     * @throws NullPointerException if the specified array is null
     */
    @Override
    @SuppressWarnings("unchecked")
    public <T> T[] toArray(T[] a) {
        fullyLock();
        try {
            int size = count.get();
            if (a.length < size)
                a = (T[])java.lang.reflect.Array.newInstance
                                                        (a.getClass().getComponentType(), size);

            int k = 0;
            for (Node<?> p = head.next; p != null; p = p.next)
                a[k++] = (T)p.item;
            // 原来这里有一个如下的校验，感觉无用
            // if (a.length > k)
            //     a[k] = null;
            return a;
        } finally {
            fullyUnlock();
        }
    }

    @Override
    public String toString() {
        fullyLock();
        try {
            return super.toString();
        } finally {
            fullyUnlock();
        }
    }

    /**
     * 清空队列
     */
    @Override
    public void clear() {
        fullyLock();
        try {
            // 这里原来的做法是全部遍历一趟，将每个节点置为空，然后将尾巴节点设置为头节点
            head.next = null;
            last.item = null;
            head = last;
            // 这里原来是 == 的判断，因为容量可变，这里改为 >=。原有的逻辑因为队列满了，
            // 添加操作会被阻断，所以在清空的时候唤醒添加线程。
            if (count.getAndSet(0) >= capacity)
                notFull.signalAll();
        } finally {
            fullyUnlock();
        }

    }

    /**
     * 获取队列内所有可用元素
     */
    @Override
    public int drainTo(Collection<? super E> c) {
        if (c == null)
            throw new NullPointerException();
        if (c == this)
            throw new IllegalArgumentException();
        Node<E> first;
        fullyLock();
        try {
            first = head.next;
            head.next = null;
            if (count.getAndSet(0) >= capacity)
                notFull.signalAll();
        } finally {
            fullyUnlock();
        }
        // Transfer the elements outside of locks
        int n = 0;
        for (Node<E> p = first; p != null; p = p.next) {
            c.add(p.item);
            p.item = null;
            ++n;
        }
        return n;
    }

    /**
     * 获取队列内可用元素，
     * @param c 集合
     * @param maxElements 个数
     * @return 满足个数的集合
     */
    @Override
    public int drainTo(Collection<? super E> c, int maxElements) {
        if (c == null)
            throw new NullPointerException();
        if (c == this)
            throw new IllegalArgumentException();
        if (maxElements <= 0)
            return 0;
        fullyLock();
        try {
            int n = 0;
            Node<E> p = head.next;
            while (p != null && n < maxElements) {
                c.add(p.item);
                p.item = null;
                p = p.next;
                ++n;
            }
            if (n != 0) {
                head.next = p;
                if (count.getAndAdd(-n) >= capacity)
                    notFull.signalAll();
            }
            return n;
        } finally {
            fullyUnlock();
        }
    }

    /**
     * Returns an iterator over the elements in this queue in proper sequence.
     * The returned {@code Iterator} is a "weakly consistent" iterator that
     * will never throw {@link java.util.ConcurrentModificationException},
     * and guarantees to traverse elements as they existed upon
     * construction of the iterator, and may (but is not guaranteed to)
     * reflect any modifications subsequent to construction.
     *
     * @return an iterator over the elements in this queue in proper sequence.
     */
    @Override
    public Iterator<E> iterator() {
        return new Itr();
    }

    private class Itr implements Iterator<E> {
        /*
         * Basic weak-consistent iterator.  At all times hold the next
         * item to hand out so that if hasNext() reports true, we will
         * still have it to return even if lost race with a take etc.
         */
        private Node<E> current;
        private Node<E> lastRet;
        private E currentElement;

        Itr() {
            final ReentrantLock putLock = BlegLinkedBlockingQueue.this.putLock;
            final ReentrantLock takeLock = BlegLinkedBlockingQueue.this.takeLock;
            putLock.lock();
            takeLock.lock();
            try {
                current = head.next;
                if (current != null)
                    currentElement = current.item;
            } finally {
                takeLock.unlock();
                putLock.unlock();
            }
        }

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public E next() {
            final ReentrantLock putLock = BlegLinkedBlockingQueue.this.putLock;
            final ReentrantLock takeLock = BlegLinkedBlockingQueue.this.takeLock;
            putLock.lock();
            takeLock.lock();
            try {
                if (current == null)
                    throw new NoSuchElementException();
                E x = currentElement;
                lastRet = current;
                current = current.next;
                currentElement = (current == null) ? null : current.item;
                return x;
            } finally {
                takeLock.unlock();
                putLock.unlock();
            }
        }

        @Override
        public void remove() {
            if (lastRet == null)
                throw new IllegalStateException();
            final ReentrantLock putLock = BlegLinkedBlockingQueue.this.putLock;
            final ReentrantLock takeLock = BlegLinkedBlockingQueue.this.takeLock;
            putLock.lock();
            takeLock.lock();
            try {
                Node<E> node = lastRet;
                lastRet = null;
                Node<E> trail = head;
                Node<E> p = head.next;
                while (p != null && p != node) {
                    trail = p;
                    p = p.next;
                }
                if (p == node) {
                    p.item = null;
                    trail.next = p.next;
                    int c = count.getAndDecrement();
                    if (c >= capacity)
                        notFull.signalAll();
                }
            } finally {
                takeLock.unlock();
                putLock.unlock();
            }
        }
    }

    /**
     * Save the state to a stream (that is, serialize it).
     *
     * @serialData The capacity is emitted (int), followed by all of
     * its elements (each an {@code Object}) in the proper order,
     * followed by a null
     * @param s the stream
     */
    private void writeObject(java.io.ObjectOutputStream s)
            throws java.io.IOException {

        fullyLock();
        try {
            // Write out any hidden stuff, plus capacity
            s.defaultWriteObject();

            // Write out all elements in the proper order.
            for (Node<E> p = head.next; p != null; p = p.next)
                s.writeObject(p.item);

            // Use trailing null as sentinel
            s.writeObject(null);
        } finally {
            fullyUnlock();
        }
    }

    /**
     * Reconstitute this queue instance from a stream (that is,
     * deserialize it).
     * @param s the stream
     */
    private void readObject(java.io.ObjectInputStream s)
            throws java.io.IOException, ClassNotFoundException {
        // Read in capacity, and any hidden stuff
        s.defaultReadObject();

        count.set(0);
        last = head = new Node<E>(null);

        // Read in all elements and place in queue
        for (;;) {
            @SuppressWarnings("unchecked")
            E item = (E)s.readObject();
            if (item == null)
                break;
            add(item);
        }
    }
}
