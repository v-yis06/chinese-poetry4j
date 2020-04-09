package com.ruoyi.framework.study.datastruct;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * @auther 易胜
 * @date 2020-04-09
 * @desc 队列的顺序存储
 */
public class QueueSeq {
    int size;
    List<Object> data;

    // 初始化
    QueueSeq init(){
        QueueSeq queueSeq = new QueueSeq();
        queueSeq.data = Lists.newArrayList();
        queueSeq.size = 0;
        return queueSeq;
    }

    // 返回左侧第一个元素（右入左出）
    Object getFirstLeft(QueueSeq queueSeq){
        if(queueSeq==null || queueSeq.size==0){
            return null;
        }
        List<Object> data = queueSeq.data;
        return data.get(0);
    }

    // 返回右侧第一个元素（右入左出）
    Object getFirstRight(QueueSeq queueSeq){
        if(queueSeq==null || queueSeq.size==0){
            return null;
        }
        List<Object> data = queueSeq.data;
        return data.get(queueSeq.size-1);
    }

    // 入列
    void add(QueueSeq queueSeq,Object obj){
        if(queueSeq==null){
            return;
        }
        List<Object> data = queueSeq.data;
        data.add(obj);
        queueSeq.size++;
    }

    // 出列
    void remove(QueueSeq queueSeq){
        if(queueSeq==null || queueSeq.size==0){
            return;
        }
        List<Object> data = queueSeq.data;
        data.remove(0);
        queueSeq.size--;
    }

    // 返回队列长度
    int getSize(QueueSeq queueSeq){
        return queueSeq.size;
    }

    // 打印
    void print(QueueSeq queueSeq){
        if(queueSeq==null || queueSeq.size==0){
            return;
        }
        List<Object> data = queueSeq.data;
        System.out.println(data.toString());
    }

    public static void main(String[] args) {

        QueueSeq queueSeq = new QueueSeq();
        QueueSeq queue = queueSeq.init();
        queueSeq.add(queue,1);
        queueSeq.add(queue,2);
        queueSeq.add(queue,3);
        queueSeq.remove(queue);
        queueSeq.remove(queue);
        queueSeq.add(queue,4);
        queueSeq.add(queue,5);

        queueSeq.print(queue);
        System.out.println(queueSeq.getFirstLeft(queue));
    }

}
