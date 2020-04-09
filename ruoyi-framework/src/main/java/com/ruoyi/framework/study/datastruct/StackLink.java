package com.ruoyi.framework.study.datastruct;

/**
 * @auther 易胜
 * @date 2020-04-09
 * @desc 栈的链式存储
 */
public class StackLink {

    LinkList linkList;
    int size;

    // 初始化
    StackLink init(){
        StackLink stackLink = new StackLink();
        LinkList linkList = new LinkList();
        stackLink.linkList = linkList.initLinkList();
        stackLink.size = 0;
        return stackLink;
    }

    // 返回栈顶元素
    LinkList.LinkNode getTop(StackLink stackLink){
        LinkList linkList = stackLink.linkList;
        return linkList.getTop(linkList);
    }

    // 入栈
    void push(StackLink stackLink, LinkList.LinkNode linkNode){
        LinkList linkList = stackLink.linkList;
        linkList.insertLinkList(linkList,linkNode,linkList.size);
        stackLink.size++;
    }

    // 出栈
    void pop(StackLink stackLink){
        LinkList linkList = stackLink.linkList;
        linkList.removeLinkList(linkList,linkList.size-1);
        stackLink.size--;
    }

    // 返回栈的元素个数
    int getSize(StackLink stackLink){
        return stackLink.size;
    }

    void print(StackLink stackLink){
        LinkList linkList = stackLink.linkList;
        linkList.printLinkList(linkList);
    }

    public static void main(String[] args) {
        StackLink stackLink = new StackLink();
        StackLink stack = stackLink.init();
        stackLink.push(stack,new LinkList.LinkNode(1));
        stackLink.push(stack,new LinkList.LinkNode(2));
        stackLink.push(stack,new LinkList.LinkNode(3));
        stackLink.push(stack,new LinkList.LinkNode(4));
        stackLink.pop(stack);

        stackLink.print(stack);
    }

}
