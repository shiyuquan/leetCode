package designpatterns.singleton;

/**
 * 单例模式代码实现（枚举方法）
 *
 * 天然线程安全，可防止反射生成实例。
 *
 * @author shiyuquan
 * Create Time: 2019/6/17 16:31
 */
public class Singleton5 {
    private Singleton5() {}

    public static Singleton5 getInstance() {
        return Singleton.INSTANCE.getInstance();
    }

    private static enum Singleton {
        INSTANCE;

        private Singleton5 singleton5;
        // JVM会保证此方法绝对只调用一次
        private Singleton() {
            singleton5 = new Singleton5();
        }

        public Singleton5 getInstance() {
            return singleton5;
        }
    }
}
