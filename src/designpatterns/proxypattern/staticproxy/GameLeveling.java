package designpatterns.proxypattern.staticproxy;

import designpatterns.proxypattern.dynamicproxy.Rank;

/**
 * 游戏代练类，代理普通玩家上分
 *
 * @author shiyuquan
 * Create Time: 2019/8/13 16:27
 */
public class GameLeveling implements Rank{

    private Rank rank;

    public GameLeveling(Rank rank) {
        this.rank = rank;
    }

    /**
     * 上分方法
     */
    @Override
    public void addScores() {
        befor();
        rank.addScores();
        after();
    }

    /**
     * 代理前置方法
     */
    private void befor() {
        System.out.println("代练之前可以做的事...比如跟其他代理打个招呼，一起当导演啥的。");
    }

    /**
     * 代理后置方法
     */
    private void after() {
        System.out.println("代练完之后可以做的事...比如去收单拿钱啥的。");
    }
}
