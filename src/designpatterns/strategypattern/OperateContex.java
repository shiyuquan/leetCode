package designpatterns.strategypattern;

/**
 * 环境角色
 *
 * @author shiyuquan
 * Create Time: 2019/8/5 22:06
 */
public class OperateContex {
    private Caculate caculate;

    public OperateContex(Caculate caculate) {
        this.caculate = caculate;
    }

    /**
     * 结合简单工厂模式
     * @param oper 操作
     */
    public OperateContex(String oper) {
        switch (oper) {
            case "+":
                caculate = new Add();
                break;
            case "-":
                caculate = new Sub();
                break;
            default:
        }
    }

    public int caculate(int a, int b) {
        return caculate.caculate(a, b);
    }

}
