import property_role.DefalultPropertyRoleParser;

import javax.script.ScriptException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author shiyuquan
 */
public class Main {

    private static final String S = "(abc.efg = \"aaa\" and ef in 456 or (\"and\"=川A12345) ) and (yu=87.2)";
    private static final String F = "abc.efg = \"aaa\" and ef in 456";

    private static List<String> splitResult = new ArrayList<>();

    public static void main(String[] args) throws ScriptException {
//        DefalultPropertyRoleParser parser = new DefalultPropertyRoleParser().setRole(S);
//        parser.resolve(S, null);
//        List<String> s = parser.getSplitResult();
//        s.forEach(o -> System.err.println(o));

        Map<String, Object> data = new HashMap<>();
        data.put("abc", "2019");
        data.put("ef", 123435643657867L);
        data.put("ec", "川A12345");
        data.put("yu", 87.256);
        String test = "abc in  [ 2019-11-26, \"safdasfd\"]";
//        String test = "(abc=\"2019-11-26\" and ef>456 or (ec=川A12345)) and (yu>87)";
        DefalultPropertyRoleParser parser = new DefalultPropertyRoleParser();
        System.err.println(parser.resolve(test, data));


    }


}