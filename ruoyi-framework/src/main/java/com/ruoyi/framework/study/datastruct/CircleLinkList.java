package com.ruoyi.framework.study.datastruct;

/**
 * @auther 易胜
 * @date 2020-04-07
 * @desc 循环链表
 */
public class CircleLinkList {


    public static class CircleLinkNode {
        String data;
        CircleLinkNode next;
        CircleLinkNode(){}
        CircleLinkNode(String data){
            this.data = data;
        }
    }

    CircleLinkNode head ;
    int size ;

    CircleLinkList(){
        CircleLinkNode head;
        int size;
    }

    CircleLinkList init(){
        CircleLinkList cLinkList = new CircleLinkList();
        cLinkList.head = new CircleLinkNode();
        cLinkList.head.next=cLinkList.head;
        cLinkList.size=0;

        return cLinkList;
    }

    void insert(CircleLinkList circleLinkList,int pos,CircleLinkNode node){
        if(circleLinkList==null || node==null){
            return;
        }
        if(pos<0 || pos<=circleLinkList.size){
            return;
        }

        int num=0;
        CircleLinkNode head = circleLinkList.head;
        while(num<pos){
            head = head.next;
            num++;
        }
        node.next=head.next;
        head.next=node;

        circleLinkList.size++;

    }

    void removeByPos(CircleLinkList circleLinkList,int pos){


    }

    void removeByValue(CircleLinkList circleLinkList,CircleLinkNode circleLinkNode){


    }

    void printCLinkList(CircleLinkList circleLinkList){


        CircleLinkNode head = circleLinkList.head;
        CircleLinkNode target ;
        for (target = head.next; target != head; target = target.next){
            System.out.println(target.data);
        }

//        int num = 0;
//        for (target = head.next; num<circleLinkList.size; target = target.next){
//            System.out.println(target.data);
//            num++;
//        }
    }

    public static void main(String[] args) {
        CircleLinkList linkList = new CircleLinkList();

        CircleLinkList list = linkList.init();
        linkList.insert(list,1,new CircleLinkNode("2"));
        linkList.insert(list,2,new CircleLinkNode("3"));
        linkList.insert(list,3,new CircleLinkNode("4"));

        linkList.printCLinkList(list);
    }
}
