package designpatterns.staticfactorymethod;

/**
 * 具体的计算方法：sqrt
 *
 * @author shiyuquan
 * Create Time: 2019/8/5 22:02
 */
public class Sqrt extends TwoNumOper {

    @Override
    public int caculate() {
        return (int) Math.sqrt(num1);
    }
}
