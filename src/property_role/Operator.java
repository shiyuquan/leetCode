package property_role;

public enum Operator {

    /**
     * 操作
     */
    EQ("eq", "=", "等于"),
    GT("gt", ">", "大于"),
    LT("lt", "<", "小于"),
    IN("in", "in", "包括"),
    LIKE("like", "like", "包含"),

    GE("ge", ">=", "大于等于"),
    LE("le", "<=", "小于等于"),

    NEQ("neq", "!=", "不等于"),
    NIN("nin", "nin", "不包括"),
    NLIKE("nlike", "nlike", "不包含"),

    ;


    String name;
    String operator;
    String remark;

    Operator(String name, String operator, String remark) {
        this.name = name;
        this.operator = operator;
        this.remark = remark;
    }

    /**
     * 根据名字获取
     * @param name 名字
     * @return 操作
     */
    public static Operator getByName(String name) {
        for (Operator o : values()) {
            if (name.equals(o.name)) { return o; }
        }
        return null;
    }

    /**
     * 根据操作获取
     * @param operator 操作
     * @return 操作
     */
    public static Operator getByOperator(String operator) {
        for (Operator o : values()) {
            if (operator.equals(o.operator)) { return o; }
        }
        return null;
    }

    /*----------------------------------------------------------------------------------------------------------------*/

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
