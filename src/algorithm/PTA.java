package algorithm;

import java.util.*;

/**
 * @author shiyuquan
 * @date 2019/4/16 13:41
 */
public class PTA {

    /**
     * 卡拉兹(Callatz)猜想：
     *
     * 对任何一个正整数 n，如果它是偶数，那么把它砍掉一半；如果它是奇数，那么把 (3n+1) 砍掉一半。这样一直反复砍下去，最后一定在某一步得到 n=1。
     * 卡拉兹在 1950 年的世界数学家大会上公布了这个猜想，传说当时耶鲁大学师生齐动员，拼命想证明这个貌似很傻很天真的命题，结果闹得学生们无心学
     * 业，一心只证 (3n+1)，以至于有人说这是一个阴谋，卡拉兹是在蓄意延缓美国数学界教学与科研的进展……
     *
     * 我们今天的题目不是证明卡拉兹猜想，而是对给定的任一不超过 1000 的正整数 n，简单地数一下，需要多少步（砍几下）才能得到 n=1？
     *
     * 输入格式：
     * 每个测试输入包含 1 个测试用例，即给出正整数 n 的值。
     *
     * 输出格式：
     * 输出从 n 计算到 1 需要的步数。
     *
     * 输入样例：
     * 3
     * 输出样例：
     * 5
     * @param num 测试数（1 ~ 1000）
     * @return 步数
     */
    public static int cllatzGuess(int num) {
        int sum = 0;
        while (num != 1) {
            if (num % 2 == 0) {
                num = num / 2;
            } else {
                num = (num * 3 + 1) / 2;
            }
            sum++;
        }
        return sum;
    }

    /**
     * 读入一个正整数 n，计算其各位数字之和，用汉语拼音写出和的每一位数字。
     *
     * 输入格式：
     * 每个测试输入包含 1 个测试用例，即给出自然数 n 的值。这里保证 n 小于 10
     * ​100
     * ​​ 。
     *
     * 输出格式：
     * 在一行内输出 n 的各位数字之和的每一位，拼音数字间有 1 空格，但一行中最后一个拼音数字后没有空格。
     *
     * 输入样例：
     * 1234567890987654321123456789
     * 输出样例：
     * yi san wu
     */
    public static void writeNum() {
        String[] py = {"ling", "yi", "er", "san", "si", "wu", "liu", "qi", "ba", "jiu"};
        Scanner input = new Scanner(System.in);
        String num = input.next();
        int sum = 0;
        for (int a = 0; a < num.length(); a++) {
            sum = sum + (num.charAt(a) - 48);
        }
        String sumString = Integer.toString(sum);
        StringBuilder s =  new StringBuilder("");
        for (int i = 0; i < sumString.length(); i++) {
            if (i != 0) {
                s.append(" ");
            }
            s.append(py[sumString.charAt(i) - 48]);
        }
        System.out.println(s.toString());
    }

    /**
     * “答案正确”是自动判题系统给出的最令人欢喜的回复。本题属于 PAT 的“答案正确”大派送 —— 只要读入的字符串满足下列条件，系统就输出“答案正确”，否则输出“答案错误”。
     *
     * 得到“答案正确”的条件是：
     *
     * 字符串中必须仅有 P、 A、 T这三种字符，不可以包含其它字符；
     * 任意形如 xPATx 的字符串都可以获得“答案正确”，其中 x 或者是空字符串，或者是仅由字母 A 组成的字符串；
     * 如果 aPbTc 是正确的，那么 aPbATca 也是正确的，其中 a、 b、 c 均或者是空字符串，或者是仅由字母 A 组成的字符串。
     * 现在就请你为 PAT 写一个自动裁判程序，判定哪些字符串是可以获得“答案正确”的。
     *
     * 输入格式：
     * 每个测试输入包含 1 个测试用例。第 1 行给出一个正整数 n (<10)，是需要检测的字符串个数。接下来每个字符串占一行，字符串长度不超过 100，且不包含空格。
     *
     * 输出格式：
     * 每个字符串的检测结果占一行，如果该字符串可以获得“答案正确”，则输出 YES，否则输出 NO。
     *
     * 输入样例：
     * 8
     * PAT
     * PAAT
     * AAPATAA
     * AAPAATAAAA
     * xPATx
     * PT
     * Whatever
     * APAAATAA
     * 输出样例：
     * YES
     * YES
     * YES
     * YES
     * NO
     * NO
     * NO
     * NO
     *
     * 思路：
     * 字符串只能有APT三种字符行且必须有PAT三种字符，缺一不可
     * 形如 xPATx 的，判对 -> P左变量数(a) = T右变量数(c)
     * 由定义的 aPbTc 正确得到的 aPbATca 正确 -> P 与 T 之间，多一个A 则 c后多一个a 为正确 -> c = a * b
     */
    public static void iWellPass() {
        Scanner input = new Scanner(System.in);
        int num = input.nextInt();
        String s[] = new String[num];
        for (int a = 0; a < num; a++) {
            s[a] = input.next();
        }
        for (int i = 0; i < num; i++) {
            int a = 0, b = 0, c = 0;
            int totalA = 0, totalP = 0, totalT = 0; // 定义用来判对字符串长度和PAT的总长度是否一样
            int indexP = 0, indexT = 0;
            for (int j = 0; j < s[i].length(); j++) {
                if (s[i].charAt(j) == 'P') {
                    indexP = j; // 记录P的位置
                    totalP++;
                }
                if (s[i].charAt(j) == 'T') {
                    indexT = j;
                    totalT++;
                }
                if (s[i].charAt(j) == 'A') {
                    totalA++;
                }
                a = indexP; // 记录a的长度
                b = indexT - indexP - 1;
                c = s[i].length() - indexT - 1;
            }
            // P 在 T 前面， APT的和=字符串的长度，T和P有且只能有一个，A不可没有，
            if (indexP - indexT < 0
                    && totalA + totalP + totalT == s[i].length()
                    && totalP == 1 && totalT == 1
                    && totalA != 0 && c == a * b) {
                System.out.println("YES");
            } else {
                System.out.println("NO");
            }
        }
    }

    /**
     * 读入 n（>0）名学生的姓名、学号、成绩，分别输出成绩最高和成绩最低学生的姓名和学号。
     *
     * 输入格式：
     * 每个测试输入包含 1 个测试用例，格式为
     *
     * 第 1 行：正整数 n
     * 第 2 行：第 1 个学生的姓名 学号 成绩
     * 第 3 行：第 2 个学生的姓名 学号 成绩
     *   ... ... ...
     * 第 n+1 行：第 n 个学生的姓名 学号 成绩
     * 其中姓名和学号均为不超过 10 个字符的字符串，成绩为 0 到 100 之间的一个整数，这里保证在一组测试用例中没有两个学生的成绩是相同的。
     *
     * 输出格式：
     * 对每个测试用例输出 2 行，第 1 行是成绩最高学生的姓名和学号，第 2 行是成绩最低学生的姓名和学号，字符串间有 1 空格。
     *
     * 输入样例：
     * 3
     * Joe Math990112 89
     * Mike CS991301 100
     * Mary EE990830 95
     * 输出样例：
     * Mike CS991301
     * Joe Math990112
     */
    public static void getGrade() {
        Scanner input = new Scanner(System.in);
        int total = input.nextInt();
        String stu[] = new String[total];
        // nextInt()读入数值，但并没有读入"\n"，因此下一次调用nextLine()会获取所在行的剩余部分，值是个空字符串。导致代码获取的输入内容错误
        // 为解决这个问题，提前调用一次方法。
        input.nextLine();
        for (int a = 0; a < total; a++) {
            stu[a] = input.nextLine();
        }
        TreeMap<Integer, String> map = new TreeMap<>();
        for (int i = 0; i < stu.length; i++) {
            String[] strings = stu[i].split(" ");
            int grade = Integer.valueOf(strings[2]);
            map.put(grade, strings[0] + " " + strings[1]);
        }
        System.out.println(map.get(map.lastKey()));
        System.out.println(map.get(map.firstKey()));
    }

    /**
     * https://pintia.cn/problem-sets/994805260223102976/problems/994805320306507776
     */
    public static void cllatzGuessPlus() {
        Scanner inpit = new Scanner(System.in);
        int n = inpit.nextInt();
        List<Integer> intList = new ArrayList();
        for (int a = 0; a < n; a++) {
            intList.add(inpit.nextInt());
        }
        List<Integer> out = new ArrayList<>();
        out.addAll(intList);
        for (int i = 0; i < intList.size(); i++) {
            int num = intList.get(i);
            while (num != 1) {
                if (num % 2 == 0) {
                    num = num / 2;
                } else {
                    num = (num * 3 + 1) / 2;
                }
                if (intList.indexOf(num) >= 0) {
                    if (out.indexOf(num) < 0) {
                        continue;
                    }
                    out.remove(out.indexOf(num));
                }
            }
        }
        Collections.sort(out);
        for (int j = out.size() - 1; j >= 0; j--)  {
            if (j != out.size() - 1) {
                System.out.print(" ");
            }
            System.out.print(out.get(j));
        }

    }

    /**
     *  https://pintia.cn/problem-sets/994805260223102976/problems/type/7
     */
    public static void specialOutInt() {
        Scanner input = new Scanner(System.in);
        int n = input.nextInt();
        StringBuilder stringBuilder = new StringBuilder("");
        int bai = n / 100;
        for (int a = 0; a < bai; a++) {
            stringBuilder.append("B");
        }
        int shi = (n / 10) % 10;
        for (int a = 0; a < shi; a++) {
            stringBuilder.append("S");
        }
        int ge = n % 10;
        for (int a = 0; a < ge; a++) {
            stringBuilder.append(a + 1);
        }
        System.out.println(stringBuilder.toString());
    }

    /**
     * https://pintia.cn/problem-sets/994805260223102976/problems/994805317546655744
     */
    public static void  primeNumberGuess() {
        Scanner input = new Scanner(System.in);
        Integer n = input.nextInt();
        List<Integer> primeNumbers = new ArrayList<>();
        // 获取素数
        boolean flag;
        for (int a = 2; a <= n; a++) {
            flag = true;
            for (int i = 2; i <= Math.sqrt(a); i++) {
                if (a % i == 0) {
                    flag = false;
                    break;
                }
            }
            if (flag == true) {
                primeNumbers.add(a);
            }
        }
        int sum = 0;
        for (int j = 1; j < primeNumbers.size(); j++) {
            if (primeNumbers.get(j) - primeNumbers.get(j - 1) == 2) {
                sum++;
            }
        }
        System.out.println(sum);
    }

    /**
     * https://translate.google.cn/#view=home&op=translate&sl=zh-CN&tl=en&text=%E5%8F%B3%E7%A7%BB
     */
    public static void arrayRightShift() {
        Scanner input = new Scanner(System.in);
        int size = input.nextInt();
        int shift = input.nextInt();
        // 修改移位大小，确认实际需要移动的位数
        while (shift - size >= 0) {
            shift = shift - size;
        }
        int l[] = new int[size];
        for (int a = 0; a < size; a++) {
            l[a] = input.nextInt();
        }
        StringBuilder s = new StringBuilder("");
        for (int i = l.length - shift; i < l.length; i ++) {
            s.append(" ").append(l[i]);
        }
        for (int j = 0; j < l.length - shift; j++) {
            s.append(" ").append(l[j]);
        }
        System.out.print(s.toString().substring(1));
    }

    /**
     * https://pintia.cn/problem-sets/994805260223102976/problems/994805314941992960
     * ascll A ~ Z - 65 ~ 90; a ~ z - 97 ~ 122
     * 空格: 32
     */
    public static void reverse() {
        Scanner input = new Scanner(System.in);
        String s = input.nextLine();
        String sc[] = s.split(" ");
        for (int a = sc.length -1; a >= 0; a--) {
            if (a != sc.length - 1) {
                System.out.print(" ");
            }
            System.out.print(sc[a]);
        }
    }

    /**
     * https://pintia.cn/problem-sets/994805260223102976/problems/994805313708867584
     */
    private static void firstOrderDerivation() {
        Scanner in = new Scanner(System.in);

        boolean isHaveOutput = false;
        while (in.hasNext()) {
            int expon = in.nextInt();
            int coef = in.nextInt();

            if (expon * coef != 0) {
                if (isHaveOutput) {
                    System.out.print(" ");
                } else {
                    isHaveOutput = true;
                }

                System.out.print(expon * coef + " " + (coef - 1));
            }
        }
        in.close();

        if (!isHaveOutput) {
            System.out.print("0 0");
        }
    }

    /**
     * https://pintia.cn/problem-sets/994805260223102976/problems/994805312417021952
     */
    private static  void aPlusBAndC() {
        Scanner sc = new Scanner(System.in);
        int T =  sc.nextInt();
        long[]a = new long[T];
        long[]b = new long[T];
        long[]c = new long[T];
        for(int i=0 ;i<T ;i++){
            a[i] = sc.nextLong();
            b[i] = sc.nextLong();
            c[i] = sc.nextLong();
        }
        for(int i=0 ;i<T ;i++){
            if(c[i]-b[i]<a[i]){
                System.out.printf("Case #%d: true\n", i+1);
            }
            else{
                System.out.printf("Case #%d: false\n", i+1);
            }
        }
    }

    /**
     * https://pintia.cn/problem-sets/994805260223102976/problems/994805311146147840
     */
    private static void numberClassify() {
        Scanner input = new Scanner(System.in);
        int a1 = 0, a2 = 0, a3 = 0, a5 = 0;
        double a4 = 0.0;
        int s2count = 0, s4count = 0;
        int num = input.nextInt();
        for (int i = 0; i < num; i++) {
            int n = input.nextInt();
            switch (n % 5) {
                case 0:
                    if (n % 2 == 0) {
                        a1 = a1 + n;
                    }
                    break;
                case 1:
                    if(s2count % 2==0){
                        a2 = a2 + n;
                    }else {
                        a2 = a2 - n;
                    }
                    s2count ++;
                    break;
                case 2:
                    a3++;
                    break;
                case 3:
                    a4 = a4 + n;
                    s4count++;
                    break;
                case 4:
                    if (n > a5) {
                        a5 = n;
                    }
                    break;
            }
        }
        System.out.print((a1 == 0 ? "N" : a1) + " ");
        if (s2count == 0 && a2 == 0) {
            System.out.print("N ");
        } else {
            System.out.print(a2 + " ");
        }
        System.out.print((a3 == 0 ? "N" : a3) + " ");
        if (s4count != 0.0) {
            a4 = a4 / s4count;
            System.out.print(String.format("%.1f ", a4));
        } else  {
            System.out.print("N ");
        }
        System.out.print(a5 == 0 ? "N" : a5);
    }

    /**
     * https://pintia.cn/problem-sets/994805260223102976/problems/994805309963354112
     */
    private static void primeNumber() {
        Scanner input = new Scanner(System.in);
        int n = input.nextInt();
        int m = input.nextInt();
        int number = 0;
        int i = 2;
        int ln = 10;
        while (true) {
            boolean p = true;
            for (int j = 2; j <= Math.sqrt(i); j++ ) {
                if (i % j == 0) {
                    p = false;
                    break;
                }
            }
            if (p == true) {
                number++;
                if (number >= n) {
                    if ((number - n) % 10 == 0 && number != n) {
                        System.out.println();
                    }
                    if ((number - n) % 10 != 0) {
                        System.out.print(" ");
                    }
                    System.out.print(i);
                }
            }
            i++;
            if (number == m) {
                break;
            }
        }
    }

    /**
     * 1014 福尔摩斯的约会
     * https://pintia.cn/problem-sets/994805260223102976/problems/994805308755394560
     * ascll A ~ Z - 65 ~ 90; a ~ z - 97 ~ 122; 0 ~ 9 - 48 ~ 57;
     * 空格: 32
     */
    private static void HolmesSDating() {
        String[] week = {"MON", "TUE", "WED", "THU", "FRI", "SAT", "SUN"};
        Scanner input = new Scanner(System.in);
        String s1 = "", s2 = "", s3 = "", s4 = "";
        s1 = input.nextLine();
        s2 = input.nextLine();
        s3 = input.nextLine();
        s4 = input.nextLine();
        int b = 0;
        for (int i = 0; i < s1.length(); i++) {
            if (s1.charAt(i) == s2.charAt(i)) {
                if (b == 1) {
                    if (s1.charAt(i) <= 57 && s1.charAt(i) >= 48) {
                        System.out.printf("%02d:", Integer.valueOf(s1.charAt(i)) - 48);
                        break;
                    }
                    if (s1.charAt(i) <= 78 && s1.charAt(i) >= 65) {
                        System.out.print(Integer.valueOf(s1.charAt(i)) - 55 + ":");
                        break;
                    }
                    continue;
                }
                if (s1.charAt(i) <= 71 && s1.charAt(i) >= 65 && b == 0) {
                    System.out.print(week[s1.charAt(i) - 65] + " ");
                    b++;
                    continue;
                }
            }
        }
        for (int i = 0; i < s3.length(); i++) {
            if (s3.charAt(i) == s4.charAt(i)) {
                if ((s3.charAt(i) <=90 && s3.charAt(i) >= 65) || (s3.charAt(i) <= 122 && s3.charAt(i) >= 97)) {
                    System.out.printf("%02d", i);
                    break;
                }
            }
        }

    }


    public static void main(String[] args) throws Exception{
        Apple a = new Apple();
        Apple b = new Apple();
        Apple c = new Apple();
        a.setWeight(50);
        b.setWeight(150);
        c.setWeight(100);
        List<Apple> apples = new ArrayList<>();
        apples.add(a);
        apples.add(b);
        apples.add(c);
        Collections.sort(apples);
        for (int i = 0; i < apples.size(); i++) {
            System.out.println(apples.get(i).getWeight());
        }
    }

    public static class Apple implements Comparable<Apple> {
        /**
         * 苹果的重量
         */
        private int  weight;

        public int getWeight() {
            return weight;
        }

        public void setWeight(int weight) {
            this.weight = weight;
        }

        /**
         * 自然排序即从小到大
         * 返回1的，代表此对象比参数对象大，排在后面，这样就可以控制降序或升序排列
         */
        @Override
        public int compareTo(Apple apple) {
            if (this.weight > apple.getWeight()) {
                return -1;
            }
            else if (this.weight < apple.getWeight()) {
                return 1;
            } else {
                return 0;
            }
        }
    }

    private static class Person implements Comparable<Person> {
        private int id;
        private int mora;
        private int talent;
        private int type;
        private int score;
        public Person(int id, int mora, int talent, int h) {
            this.id = id;
            this.mora = mora;
            this.talent = talent;
            this.score = this.mora + this.talent;
            if (mora >= h && talent >= h)
                type = 1;
            else if (mora >= h && talent < h)
                type = 2;
            else if (mora < h && talent < h
                    && mora >= talent)
                type = 3;
            else
                type = 4;
        }
        public void printInfo() {
            System.out.println(this.id + " " + this.mora + " " + this.talent);
        }
        public int compareTo(Person p) {
            if (this.type == p.type) {
                if (this.score == p.score) {
                    if (this.mora == p.mora)
                        return (this.id - p.id);
                    return (p.mora - this.mora);
                }
                return (p.score - this.score);
            }
            return (this.type - p.type);
        }
    }

}
