package com.ruoyi.framework.study.datastruct;

/**
 * @auther 易胜
 * @date 2020-03-27
 * @desc 链表
 */
public class LinkList {

    public static class LinkNode {
        Object data;
        LinkNode nextLinkNode;
        LinkNode(){

        }
        LinkNode(Object data){
            this.data = data;
            this.nextLinkNode = null;
        }
        LinkNode(LinkNode linkNode){
            this.data = linkNode.data;
            this.nextLinkNode = null;
        }
    }

    LinkNode root;
    int size;

    LinkList(){
        this.root = new LinkNode();
        this.size = 0;
    }

    LinkList initLinkList(){
        LinkList linkList = new LinkList();
        LinkNode root = new LinkNode();
        linkList.root = root;
        linkList.size = 0;

        return linkList;
    }

    int findLinkList(LinkList linkList, String data){
        if(linkList ==null){
            return -1;
        }
        if(data == null){
            return -1;
        }

        int pos = 0;
        LinkNode root = linkList.root;
        while (true){
            if(root.data==data){
                break;
            }else {
                pos++;
                root = root.nextLinkNode;
            }
        }
        return pos;

    }

    void insertLinkList(LinkList linkList, LinkNode linkNode, int pos){
        if(linkList.root==null){
            return;
        }

        // 定位插入位置
        LinkNode linkNode1 = linkList.root;
        for (int i = 0; i < pos; i++) {
            linkNode1 = linkNode1.nextLinkNode;
        }

        // 插入新节点
        linkNode.nextLinkNode = linkNode1.nextLinkNode;
        linkNode1.nextLinkNode = linkNode;
        linkList.size++;
    }

    /**
     * 删除
     */
    void removeLinkList(LinkList linkList,int pos){
        if(linkList==null){
            return;
        }
        if(pos<0 || pos>linkList.size){
            return;
        }
        // 定位删除位置
        LinkNode pCurrent = linkList.root;
        for (int i = 0; i < pos; i++) {
            pCurrent = pCurrent.nextLinkNode;
        }
        // 删除节点
        LinkNode pDel = pCurrent.nextLinkNode;
        pCurrent.nextLinkNode = pDel.nextLinkNode;
        linkList.size--;
    }

    // 返回栈顶元素
    LinkNode getTop(LinkList linkList){
        if(linkList==null){
            return null;
        }
        LinkNode currentNode = linkList.root;
        while (currentNode.nextLinkNode!=null){
            currentNode = currentNode.nextLinkNode;
        }
        return currentNode;
    }

    /**
     * 获得链表长度
     */
    int sizeLinkList(LinkList linkList){
        return linkList.size;
    }

    /**
     *  打印
     */
    void printLinkList(LinkList origionLinkList){
        if(origionLinkList==null){
            return;
        }
        LinkList linkList = copyLinkList(origionLinkList);
        LinkNode pCurrent = linkList.root;
        while (true){
            if(pCurrent==null){
                break;
            }
            System.out.println(pCurrent.data);
            pCurrent = pCurrent.nextLinkNode;
        }
    }

    /**
     * 拷贝链表
     */
    LinkList copyLinkList(LinkList linkList){
        if(linkList==null){
            return null;
        }

        LinkList resultList = new LinkList();
        LinkNode pCurrent = linkList.root;
        LinkNode rRoot = new LinkNode(linkList.root.data);
        resultList.root = rRoot;
        resultList.size++;
        for (int i = 0; i < linkList.size; i++) {
            pCurrent = pCurrent.nextLinkNode;
            if(pCurrent==null){
                break;
            }

            LinkNode rTemp = new LinkNode(pCurrent.data);
            rRoot.nextLinkNode = rTemp;
            rRoot = rRoot.nextLinkNode;
            resultList.size++;

        }

        return resultList;
    }

    public static class Person{
        LinkNode linkNode ;
        String name;
        int age;
    }

    public static void main(String[] args) {
        LinkList linkList = new LinkList();


        // 1、传统链表
//        LinkList list = linkList.initLinkList();
//        // 查找节点
//        int pos = linkList.findLinkList(list,"E");
//        System.out.println("findLinkList:"+pos);
//        // 插入节点
//        LinkNode linkNode = new LinkNode("EE");
//        linkList.insertLinkList(list, linkNode,2);
//        System.out.println("insertLinkList:");
//        linkList.printLinkList(list);
//
//        // 删除节点
//        linkList.removeLinkList(list,2);
//        System.out.println("removeLinkList:");
//        linkList.printLinkList(list);

        // 2、企业链表（对比传统链表，多了一层业务实体层）
        Person p1 = new Person();
        Person p2 = new Person();
        Person p3 = new Person();
        Person p4 = new Person();
        p1.name = "n1";
        p2.name = "n2";
        p3.name = "n3";
        p4.name = "n4";
        p1.age = 21;
        p2.age = 22;
        p3.age = 23;
        p4.age = 24;
        p1.linkNode = new LinkNode("N1");
        p2.linkNode = new LinkNode("N2");
        p3.linkNode = new LinkNode("N3");
        p4.linkNode = new LinkNode("N4");

        linkList.insertLinkList(linkList,p1.linkNode,0);
        linkList.insertLinkList(linkList,p2.linkNode,0);
        linkList.insertLinkList(linkList,p3.linkNode,0);
        linkList.insertLinkList(linkList,p4.linkNode,0);
        linkList.printLinkList(linkList);
    }
}
