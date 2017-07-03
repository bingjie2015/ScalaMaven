import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by hadoop on 17/06/21.
 */
public class testString {
    public static int s =0;
    public static void main(String[] args){
        String a = "abcdabd";
        String b = "abcacabcabd";
        System.out.println(Arrays.toString(getNext(a)));
        System.out.println(Arrays.toString(getNext(b)));
        System.out.println(KMP(a,b));
        System.out.println(KMP1(a,b));

        final ArrayList<String> aaa = new ArrayList<>();
        aaa.add("dwdwa");
        aaa.clear();
        aaa.add("wdw");
        aaa.forEach(System.out::println);
        System.out.println(aaa.toString());
        System.out.println(set(10));
        System.out.println(s);
    }
    public static int set(int n){

        s +=n;if (n==1)return 1;
        return set(n-1);
    }
    //http://blog.csdn.net/v_july_v/article/details/7041827
    // 找到K前面的最短公共前缀后缀子串
    public static int[] getNext(String ps) {

        char[] p = ps.toCharArray();

        int[] next = new int[p.length];

        next[0] =-1;

        int j = 0;
        int k = -1;
        while (j < p.length - 1) {
            if (k == -1 || p[j] == p[k]) {
                next[++j] = ++k;
            } else {
                k = next[k];
            }

        }
        return next;

    }
    public static int KMP(String ts, String ps) {
        char[] t = ts.toCharArray();
        char[] p = ps.toCharArray();
        int i = 0; // 主串的位置
        int j = 0; // 模式串的位置
        int[] next = getNext(ps);
        while (i < t.length && j < p.length) {
            if (j == -1 || t[i] == p[j]) { // 当j为-1时，要移动的是i，当然j也要归0
                i++;
                j++;
            } else {
                // i不需要回溯了
                // i = i - j + 1;
                j = -1; // j回到指定位置
            }
        }
        if (j == p.length) {
            return i - j;
        } else {
            return -1;
        }
    }
    public static int KMP1(String ts, String ps) {
        char[] t = ts.toCharArray();
        char[] p = ps.toCharArray();
        int i = 0; // 主串的位置
        int j = 0; // 模式串的位置
        int[] next = getNext(ps);
        while (i < t.length && j < p.length) {
            if (j == -1 || t[i] == p[j]) { // 当j为-1时，要移动的是i，当然j也要归0
                i++;
                j++;
            } else {
                // i不需要回溯了
                // i = i - j + 1;
                j = next[j]; // j回到指定位置
            }
        }
        if (j == p.length) {
            return i - j;
        } else {
            return -1;
        }
    }
}
