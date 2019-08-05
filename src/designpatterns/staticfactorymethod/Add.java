package designpatterns.staticfactorymethod;

/**
 * 具体的计算方法：+
 *
 * @author shiyuquan
 * Create Time: 2019/8/5 21:52
 */
public class Add extends TwoNumOper {

    @Override
    public int caculate() {
        return num1 + num2;
    }

}
