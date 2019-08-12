package designpatterns.decoratorpattern.demo1;

/**
 * 定义一个英雄，凯
 * @author shiyuquan
 * Create Time: 2019/8/12 21:54
 */
public class Kai implements Hero {

    private String name = "凯";
    /**
     * 学习技能的方法
     */
    @Override
    public void learnSkill() {
        System.out.println(name + "学习了技能！");
    }
}
