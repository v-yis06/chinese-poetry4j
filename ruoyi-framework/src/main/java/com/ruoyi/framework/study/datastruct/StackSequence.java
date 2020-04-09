package com.ruoyi.framework.study.datastruct;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * @auther 易胜
 * @date 2020-04-09
 * @desc 栈的顺序存储
 */
public class StackSequence {
    int size;
    List<Object> data;

    // 初始化
    StackSequence init(){
        StackSequence stackSequence = new StackSequence();
        stackSequence.size = 0;
        stackSequence.data = Lists.newArrayList();
        return stackSequence;
    }

    // 返回栈顶元素
    Object getTop(StackSequence stackSequence){
        if(stackSequence==null){
            return null;
        }

        int size = stackSequence.size;
        if(size==0){
            return null;
        }

        return stackSequence.data.get(size-1);
    }

    // 入栈
    void push(StackSequence stackSequence, Object obj){
        if(stackSequence==null){
            return;
        }
        stackSequence.data.add(obj);
        stackSequence.size++;
    }

    // 出栈
    void pop(StackSequence stackSequence){
        if(stackSequence==null){
            return;
        }
        stackSequence.data.remove(stackSequence.size-1);
        stackSequence.size--;
    }

    // 判断是否为空
    Boolean isEmpty(StackSequence stackSequence){
        if(stackSequence==null || stackSequence.size==0){
            return true;
        }
        return false;
    }

    // 返回元素个数
    int getSize(StackSequence stackSequence){
        return stackSequence.size;
    }

    // 打印
    void print(StackSequence stackSequence){
        if(stackSequence.isEmpty(stackSequence)){
            System.out.println("空栈");
        }else {
            List<Object> list = stackSequence.data;
            for (Object obj : list) {
                System.out.println(obj.toString());
            }
        }

    }

    public static void main(String[] args) {
        StackSequence stackSequence = new StackSequence();
        StackSequence stackSeq = stackSequence.init();
        stackSequence.push(stackSeq,1);
        stackSequence.push(stackSeq,2);
        stackSequence.push(stackSeq,3);
        stackSequence.push(stackSeq,4);

        stackSequence.pop(stackSeq);

        stackSequence.print(stackSeq);
    }

}
