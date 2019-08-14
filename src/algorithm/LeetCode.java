package algorithm;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * @Author shiyuquan
 * @Date 2018/9/25 14:06
 */
public class LeetCode {

    /**
     * 1.给定一个整数数组和一个值target，求两个下标i、j，使得a[i] + a[j] = target，返回下标。
     */
    public static int[] twoSun(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        for(int i = 0; i < nums.length; i++) {
            if(map.get(target - nums[i]) != null) {
                int[] arr = {map.get(target - nums[i]), i};
                return arr;
            }
            map.put(nums[i], i);
        }
        return null;
    }

    /**
     * 给定一个10进制整数，翻转它
     */
    public static int reverse(int x) {
        long result = 0;
        while(x != 0) {
            result = result * 10 + (x%10);
            x /= 10;
            if(result < Integer.MIN_VALUE || result > Integer.MAX_VALUE) {
                return 0;
            }
        }
        return (int)result;
    }

    /**
     * 回文数判断
     */
    public static boolean isPalindrome(int x) {
        if(x < 0) {
            return false;
        }
        int a = 0;
        int x1 = x;
        while(x1 != 0) {
            a = a * 10 + (x1 % 10);
            x1 /= 10;
        }
        return a == x;
    }

    /**
     * 罗马数字转int
     * 罗马数字规则
     * (1)相同的数字连写，所表示的数等于这些数字相加得到的数，如：Ⅲ = 3；
     * (2)小的数字在大的数字的右边，所表示的数等于这些数字相加得到的数，如：Ⅷ = 8；Ⅻ = 12；
     * (3)小的数字，（限于Ⅰ、X 和C）在大的数字的左边，所表示的数等于大数减小数得到的数，如：Ⅳ= 4；Ⅸ= 9；
     * (4)正常使用时连写的数字重复不得超过三次。（“IIII”，例外。）
     * (5)在一个数的上面画一条横线，表示这个数增值1000 倍，例如有：Ⅻ=12,000 。
     * 有几条须注意掌握；
     * (1)基本数字Ⅰ、X 、C 中的任何一个，自身连用构成数目，或者放在大数的右边连用构成数目，都不能超过三个；放在大数的左边只能用一个。
     * (2)不能把基本数字V 、L 、D 中的任何一个作为小数放在大数的左边采用相减的方法构成数目；放在大数的右边采用相加的方式构成数目，只能使用一个。
     * (3)V 和X 左边的小数字只能用Ⅰ。
     * (4)L 和C 左边的小数字只能用X。
     * (5)D 和M 左边的小数字只能用C 。
     * (6)在数字上加一横表示这个数字的1000倍。
     */
    public static int romanToInt(String s) {
        Map<Character, Integer> map = new HashMap<Character, Integer>();
        map.put('I', 1);
        map.put('V', 5);
        map.put('X', 10);
        map.put('L', 50);
        map.put('C', 100);
        map.put('D', 500);
        map.put('M', 1000);
        char[] chars = s.toCharArray();
        int sum = 0;
        int lastval = 0;
        for(char c : chars) {
            int val = map.get(c);
            if(val > lastval) {
                sum = sum - 2 * lastval + val;
            } else {
                sum += val;
            }
            lastval = val;
        }
        return sum;
    }

    /**
     * 最长公共前缀
     */
    public static String longestCommonPrefix(String[] strs) {
        StringBuilder s = new StringBuilder();
        if(strs != null && strs.length > 0) {
            Arrays.sort(strs);
            char[] first = strs[0].toCharArray();
            char[] last = strs[strs.length -1].toCharArray();
            for(int i = 0; i< Math.min(first.length, last.length); i++) {
                if (first[i] == last[i]) {
                    s.append(first[i]);
                } else {
                    return s.toString();
                }
            }
        }
        return s.toString();
    }

    /**
     * 给定一个括号序列，判断其是否合法。
     */
    public static boolean isValid(String s) {
        Stack<Character> stack = new Stack<>();
        for(char c: s.toCharArray()) {
            if(c == '('){
                stack.push(')');
            } else if(c == '[') {
                stack.push(']');
            } else if(c == '{') {
                stack.push('}');
            } else if(stack.isEmpty() || stack.pop() != c) {
                return false;
            }
        }
        return stack.isEmpty();
    }

    /**
     * 归并两个有序链表
     */
    public static ListNode mergeTwoLists(ListNode l1, ListNode l2){
        if (l1 == null && l2 == null) { return null;}
        if (l1 == null) { return l2; }
        if (l2 == null) { return l1; }
        if(l1.val < l2.val){
            l1.next = mergeTwoLists(l1.next, l2);
            return l1;
        } else{
            l2.next = mergeTwoLists(l1, l2.next);
            return l2;
        }
    }

    /**
     * 给定排序的数组nums，就地删除重复项，使每个元素只出现一次并返回新的长度。
     * 不要为另一个数组分配额外的空间
     */
    public static int removeDuplicates(int[] nums) {
        int i = nums.length > 0 ? 1 : 0;
        for(int n: nums) {
            if (n > nums[i - 1]) {
                nums[i++] = n;
            }
        }
        return i;
    }

    /**
     * 给定数组nums，就地删除重复项，并返回新的长度。
     * 不要为另一个数组分配额外的空间
     */
    public static int removeElement(int[] nums, int val) {
        int i = 0;
        for(int n :nums) {
            if(val != n){
                nums[i++] = n;
            }
        }
        return i;
    }

    /**
     * 给定字符串haystack和匹配字段needle，返回needle第一次出现的位置，没有则返回-1
     */
    public static int strStr(String haystack, String needle) {
        return haystack.indexOf(needle);
    }


    /**
     * 给定排序数组和目标值，如果找到目标，则返回索引。如果没有，请返回按顺序插入的索引。
     */
    public static int searchInsert(int[] nums, int target) {
        for(int i = 0; i < nums.length; i++) {
            if(nums[i] >= target){
                return i;
            }
        }
        return nums.length;
    }
}
