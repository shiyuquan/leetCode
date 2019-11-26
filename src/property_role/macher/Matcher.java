package property_role.macher;

/**
 * 抽象的匹配器
 *
 * @author shiyuquan
 * Create Time: 2019/8/5 21:51
 */
public interface Matcher {

    /**
     * 匹配方式
     *
     * @param source 在规则内的现有的值
     * @param targit 待匹配的值
     * @return 结果
     */
    boolean match(Object source, Object targit);

}
