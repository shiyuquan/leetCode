package util;

import java.util.ArrayList;
import java.util.List;

public class Strings {
    private Strings() {}


    /**
     * 去除字符串前后的空格
     * @param s 指定字符串
     * @return 去除前后空格的字符串
     */
    public static String superTrim(String s) {
        s = s.trim();
        while (s.startsWith("　")) {//这里判断是不是全角空格
            s = s.substring(1, s.length()).trim();
        }
        while (s.endsWith("　")) {
            s = s.substring(0, s.length() - 1).trim();
        }
        return s;
    }

    /**
     * 去除字符串前后的字符，
     * @param str 指定字符串
     * @return 去除前后空格的字符串
     */
    public static String superTrim(String str, String c, int n) {
        int i = 0;
        int j = 0;
        while (str.startsWith(c) && i != n) {
            str = str.substring(1, str.length());
            i++;
        }
        while (str.endsWith(c) && j != n) {
            str = str.substring(0, str.length() - 1);
            j++;
        }
        return str;
    }


    /**
     * 字符串分割方法
     * @param target 目标字符串
     * @param splitPointList 分割点
     * return 分割结果
     */
    public static List<String> split(String target, List<Integer> splitPointList) {

        List<String> splitResult = new ArrayList<>();

        if (splitPointList.size() == 0) {
            splitResult.add(target);
            return splitResult;
        }
        if (0 != splitPointList.get(0)) {
            String s = target.substring(0, splitPointList.get(0));
            if (!Strings.superTrim(s).isEmpty()) {
                splitResult.add(s);
            }
        }
        for (int i = 0; i < splitPointList.size(); i++) {
            int index = splitPointList.get(i);

            if (i == splitPointList.size() - 1) {
                if (index != target.length()) {
                    String s = target.substring(splitPointList.get(i), target.length());
                    if (!s.trim().isEmpty()) {
                        splitResult.add(s);
                    }
                }
                break;

            }
            String s = target.substring(splitPointList.get(i), splitPointList.get(i + 1));
            if (!s.trim().isEmpty()) {
                splitResult.add(s);
            }
        }
        return splitResult;
    }

}
