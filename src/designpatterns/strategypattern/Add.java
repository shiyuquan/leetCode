package designpatterns.strategypattern;

/**
 * 具体策略角色：+
 *
 * @author shiyuquan
 * Create Time: 2019/8/5 21:52
 */
public class Add implements Caculate {

    @Override
    public int caculate(int a, int b) {
        return a + b;
    }

}
