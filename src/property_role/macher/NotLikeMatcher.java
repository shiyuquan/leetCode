package property_role.macher;

/**
 * nlike 运算匹配类
 */
public class NotLikeMatcher implements Matcher{

    /**
     * 匹配方式
     *
     * @param source 在规则内的现有的值
     * @param targit 待匹配的值
     * @return 结果
     */
    @Override
    public boolean match(Object source, Object targit) {
        return !new LikeMatcher().match(source, targit);
    }
}
