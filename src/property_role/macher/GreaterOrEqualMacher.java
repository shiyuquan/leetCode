package property_role.macher;

/**
 * >= 运算的匹配类
 */
public class GreaterOrEqualMacher implements Matcher{

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
        if (targit instanceof Long) {
            Long l1 = Long.valueOf(source.toString());
            Long l2 = (Long) targit;
            return l2 >= l1;
        }
        if (targit instanceof Integer) {
            Integer l1 = Integer.valueOf(source.toString());
            Integer l2 = (Integer) targit;
            return l2 >= l1;
        }
        if (targit instanceof Double) {
            Double l1 = Double.valueOf(source.toString());
            Double l2 = (Double) targit;
            return l2 >= l1;
        }
        return false;
    }
}
