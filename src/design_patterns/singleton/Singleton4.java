package design_patterns.singleton;

/**
 * 单例模式代码实现（懒汉式--用的时候才去实例他）
 *
 * 优点：既避免了同步带来的性能损耗，又能够延迟加载
 *
 * @author shiyuquan
 * Create Time: 2019/6/17 16:20
 */
public class Singleton4 {
    private Singleton4() {}

    private static class Inner {
        private static Singleton4 singleton4 = new Singleton4();
    }

    public static Singleton4 getInstance() {
        return Inner.singleton4;
    }
}
