package design_patterns.singleton;

/**
 * 单例模式代码实现（懒汉式--用的时候才去实例他）
 *
 * 优点：线程安全
 * 缺点：每次获取实例都要加锁，耗费资源，其实只要实例已经生成，以后获取就不需要再锁了
 *
 * @author shiyuquan
 * Create Time: 2019/6/17 16:20
 */
public class Singleton2 {
    private static Singleton2 singleton1;

    private Singleton2() {}

    public static synchronized Singleton2 getInstance() {
        if (singleton1 == null) {
            singleton1 = new Singleton2();
        }
        return singleton1;
    }
}
