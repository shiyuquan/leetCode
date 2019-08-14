package designpatterns.proxypattern.dynamicproxy;

/**
 * @author shiyuquan
 * Create Time: 2019/8/13 16:36
 */
public class Main {
    public static void main(String[] args) {
        // 实际上分的渣渣。渣渣只管给钱上分，至于代练做了什么，管他的呢。
        Player zhazha = new Player();
        // 代上分的代练，给渣渣的号上分之前做了很多准备，但是渣渣并不管你做了啥。上完分还要处理业务。
        Rank dasheng = new DynamicProxy(zhazha).getProxy();
        // 上分！！
        dasheng.addScores();
    }
}
