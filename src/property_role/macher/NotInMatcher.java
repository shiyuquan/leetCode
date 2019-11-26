package property_role.macher;

/**
 * nin 运算的匹配类
 */
public class NotInMatcher implements Matcher {

    /**
     * 匹配方式
     *
     * @param source 在规则内的现有的值
     * @param targit 待匹配的值
     * @return 结果
     */
    @Override
    public boolean match(Object source, Object targit) {
        return false;
    }
}
