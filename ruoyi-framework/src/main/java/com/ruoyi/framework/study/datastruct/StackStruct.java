package com.ruoyi.framework.study.datastruct;

import java.util.Stack;

/**
 * @auther 易胜
 * @date 2020-03-26
 * @desc
 */
public class StackStruct {

    static boolean leastNearStackMatch(String str){
        Stack stack = new Stack();
        char[] chars = str.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            // 不匹配的字符直接跳过
            if(c!='(' && c!=')'){
                continue;
            }
            // 如果是左括弧，放入栈
            if(c=='('){
                stack.push(c);
            }else {
                if(stack.isEmpty()){
                    return false;
                }else {
                    // 如果是右括弧，从栈中弹出左括弧
                    stack.pop();
                }
            }
        }
        // 最终的栈为空，表明左右括弧数量刚好匹配一致
        if(stack.isEmpty()){
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        String str = "abcde(fg(h(jj)k)lop)";
        System.out.println("leastNearStackMatch:"+leastNearStackMatch(str));
    }
}
