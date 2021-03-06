package slidewindow;

import java.util.ArrayList;
import java.util.List;

//滑动窗口的相关题目
public class Solution {

    //题目一：最小覆盖子串,返回s中包含t中所有字符的最小长度的子串，注意t可能有重复字符
    public static String minCoverageSubString(String s, String t) {
        char[] need = new char[128];  //char数组用来记录t中某个字符出现的次数
        char[] window = new char[128];  //window数组表示当前窗口中包含的某个字符的次数

        char[] s_array = s.toCharArray();
        char[] t_array = t.toCharArray();
        for (char c : t_array) {
            need[c]++;
        }
        int k = getNumsOfNotZero(need);

        int left = 0, right = 0;  //维护一个左闭右开的窗口[left, right)
        int valid = 0;  //valid表示当前窗口中满足need要求的字符种类个数
        int start = 0, len = Integer.MAX_VALUE;  //start表示返回结果中第一个字符在s中的索引位置，len表示该子串的长度

        while (right < s_array.length) {
            char temp1 = s_array[right];
            right++;

            //更新操作
            if (need[temp1] != 0) {
                window[temp1]++;
                if (window[temp1] == need[temp1]) {
                    valid++;
                }
            }

            //判断左窗口是否要收缩
            while (valid == k) {
                //更新最小覆盖子串
                if (right - left < len) {
                    start = left;
                    len = right - left;
                }

                char temp2 = s_array[left];
                left++;

                //更新操作
                if (need[temp2] != 0) {
                    if (window[temp2] == need[temp2]) {
                        valid--;
                    }
                    window[temp2]--;
                }
            }
        }

        return len == Integer.MAX_VALUE ? "" :s.substring(start, start + len);
    }

    //题目二：给定两个字符串 s1 和 s2，写一个函数来判断 s1 是否包含 s2 的排列。
    //换句话说，第一个字符串的排列之一是第二个字符串的子串。
    public static boolean checkInclusion(String s, String t) {
        char[] need = new char[128];  //char数组用来记录t中某个字符出现的次数
        char[] window = new char[128];  //window数组表示当前窗口中包含的某个字符的次数

        char[] s_array = s.toCharArray();
        char[] t_array = t.toCharArray();
        for (char c : t_array) {
            need[c]++;
        }
        int k = getNumsOfNotZero(need);

        int left = 0, right = 0;  //维护一个左闭右开的窗口[left, right)
        int valid = 0;  //valid表示当前窗口中满足need要求的字符种类个数

        while (right < s_array.length) {
            char temp1 = s_array[right];
            right++;

            //更新操作
            if (need[temp1] != 0) {
                window[temp1]++;
                if (window[temp1] == need[temp1]) {
                    valid++;
                }
            }

            //判断左窗口是否要收缩
            while (right - left >= t.length()) {
                //更新最小覆盖子串
                if (valid == k) {
                    return true;
                }

                char temp2 = s_array[left];
                left++;

                //更新操作
                if (need[temp2] != 0) {
                    if (window[temp2] == need[temp2]) {
                        valid--;
                    }
                    window[temp2]--;
                }
            }
        }

        return false;
    }

    //题目二：给定一个字符串 s 和一个非空字符串 p，找到 s 中所有是 p 的字母异位词的子串，
    //返回这些子串的起始索引。
    public List<Integer> findAnagrams(String s, String p) {
        char[] sArray = s.toCharArray();
        char[] pArray = p.toCharArray();

        char[] need = new char[128];
        char[] window =  new char[128];
        for (int i = 0; i < pArray.length; i++) {
            need[pArray[i]]++;
        }

        int size = getNumsOfNotZero(need);

        int left = 0, right = 0;  //左闭右开区间
        int valid = 0;

        List<Integer> res = new ArrayList<>();
        while (right < sArray.length) {
            char ch = sArray[right];  //将要压入窗口的字符
            right++;

            if (need[ch] != 0) {
                if (++window[ch] == need[ch]) {
                    valid++;
                }
            }

            //收缩窗口
            while (right - left >= pArray.length) {
                if (size == valid) {
                    res.add(left);
                }

                char cp = sArray[left];  //将要弹出窗口的字符
                left++;

                if (need[cp] != 0) {
                    if (window[cp]-- == need[cp]) {
                        valid--;
                    }
                }
            }
        }
        return res;
    }

    //返回input数组中不为零的元素个数
    public static int getNumsOfNotZero(char[] input) {
        int res = 0;
        for (int i = 0; i < input.length; i++) {
            if (input[i] != 0) {
                res++;
            }
        }
        return res;
    }

    public static void main(String[] args) {
        System.out.println(minCoverageSubString("ADOBECODEBANC", "ABC"));
    }
}
