package property_role.macher;

import property_role.Constant;
import util.Strings;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
        boolean result = false;
        if (null == targit) {
            return false;
        }
        List<String> sourceItems = parseListString(source.toString());
        for (int i = 0; i < sourceItems.size(); i++) {
            if (sourceItems.get(i).trim().equals(targit.toString())) {
                result = true;
                break;
            }
        }

        return result;
    }

    /**
     * 解析数组类型的字符串
     */
    private List<String> parseListString(String listString) {
       // 用来记录小括号的数目
       int a = 0;
       // 用来记录“的数目
       int b = 0;
       // 判断字符是否在“包括范围
       boolean indqm = false;
       List<Integer> splitPointList = new ArrayList<>();


       if (!(listString.startsWith("[") && listString.endsWith("]"))) {
           String err= String.format("in语句语法错误: %s", listString);
           throw new RuntimeException(err);
       }

       listString = listString.substring(1, listString.length() - 1);
       for (int i = 0; i < listString.length(); i++) {
           char s = listString.charAt(i);
           // 处理 ,
           if (s == ',') {
               if (indqm) continue;
               Collections.addAll(splitPointList, i, i + 1);
           }
           // 处理"
           if (s == Constant.dqm) {
               indqm = !indqm;
               b++;
           }
       }

        return Strings.split(listString, splitPointList);

    }

}
