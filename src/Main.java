import designpatterns.staticfactorymethod.OperateFactory;
import designpatterns.staticfactorymethod.TwoNumOper;

/**
 * @author shiyuquan
 */
public class Main {
    public static void main(String[] args) throws Exception {
        TwoNumOper o = OperateFactory.createOper("+");
        o.num1 = 1;
        o.num2 = 2;
        System.out.println(o.caculate());
    }

}