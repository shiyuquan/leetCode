package property_role;

/**
 * 括号
 *
 * { }	Opening and closing braces
 * ( )	Opening and closing parentheses
 * [ ]	Opening and closing brackets
 */
public enum Brackets {

    /**
     * 括号
     */
    O_BRACES("o_braces", "{"),
    C_BRACES("c_braces", "}"),
    O_PARENTHESES("o_parentheses", "("),
    C_PARENTHESES("c_parentheses", ")"),
    O_BRACKETS("o_brackets", "["),
    C_BRACKETS("c_brackets", "]"),

    ;


    String name;
    String brackets;

    Brackets(String name, String brackets) {
        this.name = name;
        this.brackets = brackets;
    }

    /**
     * 根据名字获取
     * @param name 名字
     * @return 操作
     */
    public static Brackets getByName(String name) {
        for (Brackets o : values()) {
            if (name.equals(o.name)) { return o; }
        }
        return null;
    }

    /**
     * 根据括号获取
     * @param brackets 括号
     * @return 操作
     */
    public static Brackets getByBrackets(String brackets) {
        for (Brackets o : values()) {
            if (brackets.equals(o.brackets)) { return o; }
        }
        return null;
    }
}
