package designpatterns.decoratorpattern.demo1;

/**
 * 一技能类，装饰英雄技能
 * @author shiyuquan
 * Create Time: 2019/8/12 21:56
 */
public class FirstSkill extends HeroSkill {

    /**
     * 构造函数接受一个英雄，我理解为给英雄包装一个 一技能修饰的英雄技能 外壳
     *
     * @param hero 英雄
     */
    public FirstSkill(Hero hero) {
        super(hero);
    }

    /**
     * 学习技能的方法
     */
    @Override
    public void learnSkill() {
        System.out.println("学习了一技能!");
        super.learnSkill();
    }
}
