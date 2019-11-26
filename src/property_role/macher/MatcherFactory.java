package property_role.macher;

import property_role.Operator;

/**
 * 匹配器工厂
 */
public class MatcherFactory {

    public static Matcher getMatcher(String ope) {
        Matcher matcher = null;
        Operator operator = Operator.getByOperator(ope);
        if (null == operator) {
            String s = String.format("出现未知运算 %s 请检查公式！", ope);
            throw new RuntimeException(s);
        }
        switch (operator) {
            case EQ:
                matcher = new EqualsMatcher();
                break;
            case GT:
                matcher = new GreaterMatcher();
                break;
            case LT:
                matcher = new LessMacher();
                break;
            case IN:
                matcher = new InMatcher();
                break;
            case LIKE:
                matcher = new LikeMatcher();
                break;
            case GE:
                matcher = new GreaterOrEqualMacher();
                break;
            case LE:
                matcher = new LessOrEqualMacher();
                break;
            case NEQ:
                matcher = new NotEqualMatcher();
                break;
            case NIN:
                matcher = new NotInMatcher();
                break;
            case NLIKE:
                matcher = new NotLikeMatcher();
                break;
            default:
                 break;
        }
        return matcher;

    }
}
