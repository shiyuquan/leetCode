package designpatterns.decoratorpattern.demo1;

/**
 * 英雄的包装类 （英雄技能）
 * @author shiyuquan
 * Create Time: 2019/8/12 21:49
 */
public class HeroSkill implements Hero {

    public Hero hero;

    /**
     * 构造函数接受一个英雄，我理解为给英雄包装一个 英雄技能的 外壳
     * @param hero 英雄
     */
    public HeroSkill(Hero hero) {
        this.hero = hero;
    }

    /**
     * 学习技能的方法
     */
    @Override
    public void learnSkill() {
        hero.learnSkill();
    }
}
