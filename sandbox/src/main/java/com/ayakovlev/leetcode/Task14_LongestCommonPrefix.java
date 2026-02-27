package com.ayakovlev.leetcode;

import java.util.Arrays;

/*
https://leetcode.com/problems/longest-common-prefix/description/
* 14. Longest Common Prefix
*Write a function to find the longest common prefix string amongst an array of strings.

If there is no common prefix, return an empty string "".



Example 1:

Input: strs = ["flower","flow","flight"]
Output: "fl"
Example 2:

Input: strs = ["dog","racecar","car"]
Output: ""
Explanation: There is no common prefix among the input strings.


Constraints:

1 <= strs.length <= 200
0 <= strs[i].length <= 200
strs[i] consists of only lowercase English letters.
* */
public class Task14_LongestCommonPrefix {
    public static void main(String[] args){
        System.out.println("* 14. Longest Common Prefix");
        String[] strs = new String[]{"abca", "abc", "abcaa"};
        strs = new String[]{"flower","flow","flight"};
        strs = new String[]{"dog","racecar","car"};
        System.out.println("strs: " + Arrays.toString(strs));
        Task14_LongestCommonPrefix t = new Task14_LongestCommonPrefix();
        String res = t.longestCommonPrefix(strs);
        System.out.println("res: " + res);
    }

    public String longestCommonPrefix(String[] strs) {
        if(strs == null || strs.length == 0 || strs[0] == null || strs[0].length() == 0){
            return "";
        }
        if(strs.length == 1){
            return strs[0];
        }
        // берём i-ый символ из первой строки -> c
        // бежим по всем остальным строкам и проверяем в них i-ый символ.
        // Если он везде равен c, заносим его в результирующий StringBuffer,
        // если хотя бы в одной строке есть несовпадение => выходим из метода и возвращаем текущий StringBuffer.
        StringBuffer sb = new StringBuffer();
        for(int i = 0; i < strs[0].length(); i++){
            char c = strs[0].charAt(i);
            for(int j = 1; j < strs.length; j++){
//                System.out.println();
//                System.out.println("strs[j]: " + strs[j]);
//                System.out.println("strs[j].length(): " + strs[j].length());
//                System.out.println("i: " + i);
//                System.out.println("strs[j].charAt(i): " + strs[j].charAt(i));
                if(strs[j].length() <= i || strs[j].charAt(i) != c){
                    return sb.toString();
                }
            }
            sb.append(c);
        }
        return sb.toString();
    }
}
