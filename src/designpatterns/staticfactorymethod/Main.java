package designpatterns.staticfactorymethod;

/**
 * @author shiyuquan
 * Create Time: 2019/8/6 20:49
 */
public class Main {
    public static void main(String[] args) {
        TwoNumOper o = OperateFactory.createOper("+");
        o.num1 = 1;
        o.num2 = 2;
        System.out.println(o.caculate());
    }
}
