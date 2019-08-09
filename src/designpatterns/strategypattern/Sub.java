package designpatterns.strategypattern;

/**
 * 具体策略角色：-
 *
 * @author shiyuquan
 * Create Time: 2019/8/5 22:21
 */
public class Sub implements Caculate{
    @Override
    public int caculate(int a, int b) {
        return a - b;
    }
}
