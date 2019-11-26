package property_role;

import property_role.macher.Matcher;
import property_role.macher.MatcherFactory;

/**
 * 规则项目，() and or 没有左右item
 */
public class RoleItem {

    /** 左子项 */
    private String leftItem;
    /** 右子项 */
    private Object rightItem;
    /** 操作符 */
    private String operator;

    /** 标识是否纯符号项 */
    private boolean pureSymbolItem;
    /** 待匹配的数据 */
    private Object targitValue;
    /** 匹配完成的结果 */
    private boolean match = false;

    /**
     * 进行匹配的方法
     * @return 匹配结果
     */
    public boolean match() {
        if (!pureSymbolItem) {
            Matcher m =  MatcherFactory.getMatcher(operator);
            match = m.match(rightItem, targitValue);
        }
        return match;
    }

    /*----------------------------------------------------------------------------------------------------------------*/

    public String getLeftItem() {
        return leftItem;
    }

    public void setLeftItem(String leftItem) {
        this.leftItem = leftItem;
    }

    public Object getRightItem() {
        return rightItem;
    }

    public void setRightItem(Object rightItem) {
        this.rightItem = rightItem;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public boolean isMatch() {
        return match;
    }

    public void setMatch(boolean match) {
        this.match = match;
    }

    public Object getTargitValue() {
        return targitValue;
    }

    public void setTargitValue(Object targitValue) {
        this.targitValue = targitValue;
    }

    public boolean isPureSymbolItem() {
        return pureSymbolItem;
    }

    public void setPureSymbolItem(boolean pureSymbolItem) {
        this.pureSymbolItem = pureSymbolItem;
    }

    @Override
    public String toString() {
        return "RoleItem{" +
                "leftItem='" + leftItem + '\'' +
                ", rightItem=" + rightItem +
                ", operator='" + operator + '\'' +
                '}';
    }
}
