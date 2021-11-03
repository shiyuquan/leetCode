package multithreading.demo.threadlocal.demo;

import java.util.HashMap;
import java.util.Map;

/**
 * InheritableThreadLocal: 子线程可继承的 threadLocal，多线程使用线程池下存在脏读现象，谨慎使用！
 *
 * @author shiyuquan
 * @since 2021/5/27 2:54 下午
 */
public class ThreadLocalAttribute {

    private static final ThreadLocal<Map<String, String>> LOCAL_ATTRIBUTE = new ThreadLocal<>();

    /**
     * 清空当前线程的存储对象，重要操作，threadLocal 使用完后一定要调用
     */
    public static void clearLocalAttribute() {
        LOCAL_ATTRIBUTE.remove();
    }

    public static Map<String, String> getAttributes() {
        Map<String, String> attr = LOCAL_ATTRIBUTE.get();
        if (attr == null) {
            attr = createEmptyAttributes();
            LOCAL_ATTRIBUTE.set(attr);
        }
        return attr;
    }

    private static Map<String, String> createEmptyAttributes() {
        return new HashMap<>();
    }

    public static Object get(String name) {
        System.err.println(getAttributes().get(name));
        return getAttributes().get(name);
    }

    public static void set(String name, String o) {
        getAttributes().put(name, o);
    }

}
