package property_role.macher;

/**
 * in 运算的匹配类
 */
public class InMatcher implements Matcher{

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

    /**
     * 解析规则
     */
    private void parseRole() {
//        // 用来记录小括号的数目
//        int a = 0;
//        // 用来记录“的数目
//        int b = 0;
//        // 判断字符是否在“包括范围
//        boolean indqm = false;
//        List<Integer> splitPointList = new ArrayList<>();
//
//        for (int i = 0; i < role.length(); i++) {
//            char s = role.charAt(i);
//
//            // 处理"
//            if (s == Constant.dqm) {
//                indqm = !indqm;
//                b++;
//            }
//        }

    }

}
