package designpatterns.proxypattern.dynamicproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author shiyuquan
 * Create Time: 2019/8/13 16:43
 */
public class DynamicProxy implements InvocationHandler {

    private Object target;

    public DynamicProxy(Object target) {
        this.target = target;
    }

    /**
     * 获取动态的代练对象
     * @param <T>
     * @return
     */
    public <T> T getProxy() {
        return (T) Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), this);
    }

    /**
     * 代理执行方法
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        befor();
        Object result = method.invoke(target, args);
        after();
        return result;
    }

    /**
     * 代理前置方法
     */
    private void befor() {
        System.out.println("代练之前可以做的事...比如跟其他代理打个招呼，一起当导演啥的。");
    }

    /**
     * 代理后置方法
     */
    private void after() {
        System.out.println("代练完之后可以做的事...比如去收单拿钱啥的。");
    }
}
