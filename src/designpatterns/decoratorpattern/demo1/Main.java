package designpatterns.decoratorpattern.demo1;

/**
 * @author shiyuquan
 * Create Time: 2019/8/12 22:06
 */
public class Main {
    public static void main(String[] args) {
        // 定义一个英雄
        Hero kai = new Kai();
        // 给英雄装饰英雄技能，变成一个有英雄技能的英雄
        Hero skillHero = new HeroSkill(kai);
        // 给技能英雄技能进行装饰，装饰一技能！
        Hero firstSkillHero = new FirstSkill(skillHero);
        // 给一技能英雄技能进行装饰，装饰二技能！
        Hero secondSkillHero = new SecondSkill(firstSkillHero);
        secondSkillHero.learnSkill();

        /** ----------------- 一个有二技能的、有一技能的、技能英雄 -- 凯 -------------------- **/
        Hero hero = new SecondSkill(new FirstSkill(new HeroSkill(new Kai())));
        hero.learnSkill();
    }
}
