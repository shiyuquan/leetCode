package designpatterns.staticfactorymethod;

/**
 * 具体的计算方法：-
 *
 * @author shiyuquan
 * Create Time: 2019/8/5 22:21
 */
public class Sub extends TwoNumOper {
    @Override
    public int caculate() {
        return num1 - num2;
    }
}
