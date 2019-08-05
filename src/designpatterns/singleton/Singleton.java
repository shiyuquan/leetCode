package designpatterns.singleton;

/**
 * 单例的实现（饿汉式--系统加载就单例了）
 * ①声明静态私有类变量，且立即实例化，保证实例化一次
 * ②私有构造，防止外部实例化（通过反射是可以实例化的，不考虑此种情况）
 * ③提供public的getInstance（）方法供外部获取单例实例
 *
 * 好处：线程安全；获取实例速度快
 * 缺点：类加载即初始化实例，内存浪费
 *
 * @author shiyuquan
 * Create Time: 2019/6/17 16:14
 */
public class Singleton {
    private static final Singleton singleton = new Singleton();

    private Singleton() {}

    public static Singleton getInstance() {
        return singleton;
    }
}
