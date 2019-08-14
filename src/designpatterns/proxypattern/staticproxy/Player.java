package designpatterns.proxypattern.staticproxy;

/**
 * 普通玩家，他只关注上分没有...
 *
 * @author shiyuquan
 * Create Time: 2019/8/13 16:23
 */
public class Player implements Rank {
    /**
     * 上分方法
     */
    @Override
    public void addScores() {
        System.out.println("青铜渣上分了。");
    }
}
