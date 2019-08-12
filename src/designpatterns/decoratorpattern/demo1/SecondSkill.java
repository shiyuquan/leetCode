package designpatterns.decoratorpattern.demo1;

/**
 * 二技能，装饰英雄技能
 * @author shiyuquan
 * Create Time: 2019/8/12 22:01
 */
public class SecondSkill extends HeroSkill {

    /**
     * 构造函数接受一个英雄，我理解为给英雄包装一个 二技能装饰的英雄技能 外壳
     *
     * @param hero 英雄
     */
    public SecondSkill(Hero hero) {
        super(hero);
    }

    /**
     * 学习技能的方法
     */
    @Override
    public void learnSkill() {
        System.out.println("学习了二技能！");
        super.learnSkill();
    }
}
