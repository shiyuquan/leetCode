package designpatterns.strategypattern;

/**
 * @author shiyuquan
 * Create Time: 2019/8/6 20:49
 */
public class Main {
    public static void main(String[] args) {
        OperateContex c = new OperateContex(new Add());

        System.out.println(c.caculate(1, 2));
    }
}
