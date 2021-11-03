package property_role;

import util.Strings;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static property_role.Constant.and;
import static property_role.Constant.or;

/**
 * 默认的属性解析器
 */
public class DefaultPropertyRoleParser implements PropertyRoleParser {

    /** 规则解析截取出来的字符串集合 */
    private List<String> splitResult = new ArrayList<>();
    /** 待解析的规则 */
    private String role;
    /** 待匹配的数据 */
    private Map<String, Object> data;
    /** 解析出来的规则项目 */
    private List<RoleItem> roleItems = new ArrayList<>();

    /** JavaScript 引擎 */
    private static ScriptEngine jse = new ScriptEngineManager().getEngineByName("JavaScript");

    /**
     * 解析方法
     * @param role 指定的规则
     * @param data 待匹配的数据
     * @return 结果
     */
    @Override
    public Object resolve(String role, Map<String, Object> data) {
        this.role = role;
        this.data = data;
        System.err.println(role);
        parseRole();
        splitResult.forEach(o -> {
            RoleItem item = generateRoleItem(o);
            item.match();
            roleItems.add(item);
        });
        String s = combRoleItem();
        System.err.println(s);
        try {
            return jse.eval(s);
        } catch (ScriptException e) {
            throw new RuntimeException("公式计算错误:", e);
        }
    }

    /**
     * 组合roleItems 的规则项，将匹配成功的项设置为1，不匹配的设置为0， and 转 &， or 转 |
     * @return 组合出来的公式
     */
    private String combRoleItem() {
        StringBuilder sb = new StringBuilder();
        roleItems.forEach(i -> {
            if (i.isPureSymbolItem()) {
                sb.append(" ");
                String op = i.getOperator();
                if (and.equals(op)) {
                    sb.append("&");
                } else if (or.equals(op)) {
                    sb.append("|");
                } else {
                    sb.append(op);
                }
                sb.append(" ");
            } else {
                sb.append(i.isMatch() ? "1" : "0");
            }
        });
        return sb.toString();
    }

    /**
     * 解析规则
     */
    private void parseRole() {
        // 用来记录小括号的数目
        int a = 0;
        // 用来记录“的数目
        int b = 0;
        // 判断字符是否在“包括范围
        boolean indqm = false;
        List<Integer> splitPointList = new ArrayList<>();

        for (int i = 0; i < role.length(); i++) {
            char s = role.charAt(i);
            // 处理 （）
            if (s == Constant.lc) {
                if (indqm) continue;
                Collections.addAll(splitPointList, i, i + 1);
                a++;
            }
            if (s == Constant.rc) {
                if (indqm) continue;
                Collections.addAll(splitPointList, i, i + 1);
                a--;
            }
            // 处理and
            if (s == 'a' || s == 'A') {
                if (indqm) continue;
                try {
                    char n = role.charAt(i + 1);
                    char d = role.charAt(i + 2);
                    if ((n == 'n' || n == 'N') && (d == 'd' || d == 'D')) {
                        Collections.addAll(splitPointList, i, i + 3);
                    }
                } catch (StringIndexOutOfBoundsException e) {
                    continue;
                }

            }
            // 处理or
            if (s == 'o' || s == 'O') {
                if (indqm) continue;
                try {
                    char r = role.charAt(i + 1);
                    if (r == 'r' || r == 'R') {
                        Collections.addAll(splitPointList, i, i + 2);
                    }
                } catch (StringIndexOutOfBoundsException e) {
                    continue;
                }
            }
            // 处理"
            if (s == Constant.dqm) {
                indqm = !indqm;
                b++;
            }
        }
        if (!(a == 0 && b % 2 == 0)) {
            throw new RuntimeException("公式错误");
        }
        splitResult = Strings.split(role, splitPointList);

    }


    /**
     * 解析规则
     */
    private List<String> parseItem(String item) {

        // 判断字符是否在“包括范围
        boolean indqm = false;

        List<Integer> splitPointList = new ArrayList<>();

        // 去除前后的空格
        item = Strings.superTrim(item);
        for (int ii = 0; ii < item.length(); ii++) {
            char s = item.charAt(ii);
            // 处理 =
            if (s == '=') {
                if (indqm) continue;
                Collections.addAll(splitPointList, ii, ii + 1);
            }
            // 处理 > >=
            if (s == '>') {
                if (indqm) continue;
                try {
                    char eq = item.charAt(ii + 1);
                    if (eq == '=') {
                        Collections.addAll(splitPointList, ii, ii + 2);
                    } else {
                        Collections.addAll(splitPointList, ii, ii + 1);
                    }
                } catch (StringIndexOutOfBoundsException e) {
                    continue;
                }
            }
            // 处理 < <=
            if (s == '<') {
                if (indqm) continue;
                try {
                    char eq = item.charAt(ii + 1);
                    if (eq == '=') {
                        Collections.addAll(splitPointList, ii, ii + 2);
                    } else {
                        Collections.addAll(splitPointList, ii, ii + 1);
                    }
                } catch (StringIndexOutOfBoundsException e) {
                    continue;
                }
            }
            // 处理 !=
            if (s == '!') {
                if (indqm) continue;
                try {
                    char eq = item.charAt(ii + 1);
                    if (eq == '=') {
                        Collections.addAll(splitPointList, ii, ii + 2);
                    }
                } catch (StringIndexOutOfBoundsException e) {
                    continue;
                }
            }

            // 对 in like nin nlike 的判断，需要判断单词前后是否有空格，有空格设置切割点，否则不设
            // 处理 in
            if (s == 'i' || s == 'I') {
                if (indqm) continue;
                try {
                    char n = item.charAt(ii + 1);
                    if (n == 'n' || n == 'N') {
                        if (ii == 0) {
                            continue;
                        }
                        if (item.charAt(ii + 2) == ' ' && item.charAt(ii - 1) == ' ') {
                            Collections.addAll(splitPointList, ii, ii + 2);
                        }
                    }
                } catch (StringIndexOutOfBoundsException e) {
                    continue;
                }
            }
            // 处理 like
            if (s == 'l' || s == 'L') {
                if (indqm) continue;
                try {
                    char i = item.charAt(ii + 1);
                    char k = item.charAt(ii + 2);
                    char e = item.charAt(ii + 3);
                    if ((i == 'i' || i == 'I') && (k == 'k' || k == 'K') && (e == 'e' || e == 'E')) {
                        if (ii == 0) {
                            continue;
                        }
                        if (item.charAt(ii + 4) == ' ' && item.charAt(ii - 1) == ' ') {
                            Collections.addAll(splitPointList, ii, ii + 4);
                        }
                    }
                } catch (StringIndexOutOfBoundsException e) {
                    continue;
                }
            }
            // 处理 nin nlike
            if (s == 'n' || s == 'N') {
                if (indqm) continue;
                try {
                    char temp = item.charAt(ii + 1);
                    if (temp == 'i' || temp == 'I') {
                        char n = item.charAt(ii + 2);
                        if (n == 'n' || n == 'N') {
                            if (ii == 0) {
                                continue;
                            }
                            if (item.charAt(ii + 3) == ' ' && item.charAt(ii - 1) == ' ') {
                                Collections.addAll(splitPointList, ii, ii + 3);
                            }
                        }
                    }
                    if (temp == 'l' || temp == 'L') {
                        char i = item.charAt(ii + 2);
                        char k = item.charAt(ii + 3);
                        char e = item.charAt(ii + 4);
                        if ((i == 'i' || i == 'I') && (k == 'k' || k == 'K') && (e == 'e' || e == 'E')) {
                            if (ii == 0) {
                                continue;
                            }
                            if (item.charAt(ii + 4) == ' ' && item.charAt(ii - 1) == ' ') {
                                Collections.addAll(splitPointList, ii, ii + 4);
                            }
                        }
                    }
                } catch (StringIndexOutOfBoundsException e) {
                    continue;
                }
            }

            // 处理"
            if (s == Constant.dqm) {
                indqm = !indqm;
            }

        }

        return Strings.split(item, splitPointList);

    }

    /**
     * 给定内容，生成规则项
     * @param s 内容
     * @return 规则项
     */
    private RoleItem generateRoleItem(String s) {
        RoleItem roleItem = new RoleItem();
        if (s.equals(Constant.LC)
                || s.equals(Constant.RC)
                || s.equals(and)
                || s.equals(Constant.or)
                || s.equals(Constant.AND)
                || s.equals(Constant.OR)) {
            roleItem.setOperator(s);
            roleItem.setPureSymbolItem(true);
            return roleItem;
        }
        List<String> result = parseItem(s);

        if (result.size() != 3) {
            throw new RuntimeException("规则项出现错误，请检查规则");
        }
        roleItem.setLeftItem(Strings.superTrim(Strings.superTrim(result.get(0)), "\"", 1));
        roleItem.setOperator(Strings.superTrim(result.get(1)));
        roleItem.setRightItem(Strings.superTrim(Strings.superTrim(result.get(2)), "\"", 1));

        roleItem.setPureSymbolItem(false);
        Object o = data.get(Strings.superTrim(result.get(0)));
        roleItem.setTargitValue(o);
        return roleItem;
    }

    /*----------------------------------------------------------------------------------------------------------------*/

    public List<String> getSplitResult() {
        return splitResult;
    }

    public DefaultPropertyRoleParser setSplitResult(List<String> splitResult) {
        this.splitResult = splitResult;
        return this;
    }

    public String getRole() {
        return role;
    }

    public DefaultPropertyRoleParser setRole(String role) {
        this.role = role;
        return this;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public DefaultPropertyRoleParser setData(Map<String, Object> data) {
        this.data = data;
        return this;
    }
}
