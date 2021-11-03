package property_role.macher;

/**
 * like 运算的匹配类
 */
public class LikeMatcher implements Matcher {

    /**
     * 匹配方式
     *
     * @param source 在规则内的现有的值
     * @param targit 待匹配的值
     * @return 结果
     */
    @Override
    public boolean match(Object source, Object targit) {
        if (null == targit) {
            return false;
        }
        String s1 = source.toString();
        String s2 = targit.toString();
        return s1.contains(s2);

    }
}
