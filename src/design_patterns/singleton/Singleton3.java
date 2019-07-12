package design_patterns.singleton;

/**
 * 单例模式代码实现（懒汉式--用的时候才去实例他）
 *
 * 优点：线程安全，进行双重检查，保证只在实例未初始化前进行同步，效率高
 * 缺点：还是实例非空判断，耗费一定资源
 *
 * @author shiyuquan
 * Create Time: 2019/6/17 16:20
 */
public class Singleton3 {
    private static Singleton3 singleton1;

    private Singleton3() {}

    public static Singleton3 getInstance() {
        if (singleton1 == null) {
            synchronized (Singleton3.class) {
                if (singleton1 == null) {
                    singleton1 = new Singleton3();
                }
            }
        }
        return singleton1;
    }
}
