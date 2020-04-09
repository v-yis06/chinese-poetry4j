package com.ruoyi.framework.study.datastruct;

/**
 * @auther 易胜
 * @date 2020-04-07
 * @desc 循环链表
 */
public class CircleLinkList {


    public static class CircleLinkNode {
        Object data;
        CircleLinkNode next;
        CircleLinkNode(){}
        CircleLinkNode(Object data){
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

    CircleLinkNode removeByPos(CircleLinkList circleLinkList,int pos){
        if(circleLinkList==null){
            return null;
        }
        if(pos<0 || pos>circleLinkList.size){
            return null;
        }

        int num=1;
        CircleLinkNode head = circleLinkList.head;
        while(num<pos){
            head = head.next;
            num++;
        }
        // 断链换链
        CircleLinkNode deletedNode=head.next;
        head.next=deletedNode.next;
        circleLinkList.size--;

        return deletedNode;
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

    /**
     * @auther 易胜
     * @date 2020-04-08
     * @desc 约瑟夫杀人问题
     */
    static class JosefDecreaseProblem{

        int sumCount;
        int stepCount;
        CircleLinkList circleLinkList;
        JosefDecreaseProblem(){
            circleLinkList = new CircleLinkList();
        }
        JosefDecreaseProblem(int sumCount,int stepCount){
            this.sumCount = sumCount;
            this.stepCount = stepCount;
            circleLinkList = new CircleLinkList();
        }

        /**
         *  约瑟夫问题初始化
         * @return
         */
        CircleLinkList initJosef(){

            CircleLinkList list = circleLinkList.init();
            int num = 1;
            while (num<=this.sumCount){

                circleLinkList.insert(list,num , new CircleLinkNode(num));
                num++;
            }

            return list;
        }

        void decreaseJosef(CircleLinkList list){

            if(stepCount>list.size || stepCount<=0){
                return;
            }

            int pos = this.stepCount;
            while(list.size>=this.stepCount){

                CircleLinkNode deletedNode = circleLinkList.removeByPos(list,pos);
//                list.head.next = deletedNode.next;
                // TODO:
            }

            circleLinkList.printCLinkList(list);
        }
    }

    public static void main(String[] args) {
        CircleLinkList linkList = new CircleLinkList();

//        CircleLinkList list = linkList.init();
//        linkList.insert(list,1,new CircleLinkNode("2"));
//        linkList.insert(list,2,new CircleLinkNode("3"));
//        linkList.insert(list,3,new CircleLinkNode("4"));
//        linkList.removeByPos(linkList,2);
//        linkList.printCLinkList(list);

        JosefDecreaseProblem josefDecreaseProblem = new JosefDecreaseProblem(6,2);
        CircleLinkList list = josefDecreaseProblem.initJosef();
        josefDecreaseProblem.decreaseJosef(list);
    }
}
