package designpatterns.staticfactorymethod;

/**
 * 操作工厂
 *
 * @author shiyuquan
 * Create Time: 2019/8/5 22:06
 */
public class OperateFactory {

    public static TwoNumOper createOper(String oper) {
        TwoNumOper to = null;
        switch (oper) {
            case "+":
                to = new Add();
                break;
            case "-":
                to = new Sub();
                break;
            case ".":
                to = new Sqrt();
                break;
            default:
                return null;
        }
        return to;
    }

}
