package com.ruoyi.framework.study.algorithm;

/**
 * @auther 易胜
 * @date 2020-03-26
 * @desc
 */
public class BinaryTree {

    int leafCount = 0,levelCount = 0;

    public static class Node {
        public Node(String data){
            this.data = data;
        }

        public String data;

        public Node leftNode;

        public Node rightNode;

    }

    /**
     *  创建二叉树
     * @return
     */
    private static Node createBinaryTree() {
        Node root = new Node("A");
        Node n1 = new Node("b");
        Node n2 = new Node("c");
        Node n3 = new Node("d");
        Node n4 = new Node("e");
        Node n5 = new Node("f");
        Node n6 = new Node("g");
        Node n7 = new Node("h");
        Node n8 = new Node("i");
        Node n9 = new Node("j");

        root.leftNode = n1;
        root.rightNode = n2;

        n1.leftNode = n3;
        n1.rightNode = n4;

        n2.leftNode = n5;
        n2.rightNode = n6;

        n3.leftNode = n7;
        n3.rightNode = n8;

        n4.leftNode = n9;
        return root;
    }

    /**
     *  3大遍历方式（先序、中序、后序）
     * @param root
     */
    void  recursion(Node root){
        if(root==null){
            return;
        }

        // DoSomethingwithroot
        System.out.print(root.data);

        // 遍历左节点
        recursion(root.leftNode);

        // DoSomethingwithroot

        // 遍历右节点
        recursion(root.rightNode);

        // DoSomethingwithroot
    }

    void caculateLeafCount(Node root){

        if(root==null){
            return;
        }
        if(root.leftNode==null && root.rightNode==null){
            leafCount++;
        }

        // 遍历左节点
        caculateLeafCount(root.leftNode);

        // 遍历右节点
        caculateLeafCount(root.rightNode);
    }

    /**
     * 1.一颗树只有一个节点,它的深度是1;
     * 2.二叉树的根节点只有左子树而没有右子树,那么可以判断,二叉树的深度应该是其左子树的深度加1;
     * 3.二叉树的根节点只有右子树而没有左子树,那么可以判断,那么二叉树的深度应该是其右树的深度加1;
     * 4.二叉树的根节点既有右子树又有左子树,那么可以判断,那么二叉树的深度应该是其左右子树的深度较大值加1。
     * @param root
     */
    int caculateLevelCount(Node root){

        if(root!=null && root.leftNode==null && root.rightNode==null){
            return 1;
        }

        if(root.leftNode!=null && root.rightNode==null){
            return caculateLevelCount(root.leftNode)+1;
        }

        if(root.leftNode==null && root.rightNode!=null){
            return caculateLevelCount(root.rightNode)+1;
        }

        // 遍历左节点
        int leftCount = caculateLevelCount(root.leftNode);

        // 遍历右节点
        int rightCount = caculateLevelCount(root.rightNode);

        if(leftCount>rightCount){
            return leftCount+1;
        }else {
            return rightCount+1;
        }
    }

    Node copyBinaryTree(Node root){
        if(root==null){
            return null;
        }

        Node leftNode = copyBinaryTree(root.leftNode);
        Node rightNode = copyBinaryTree(root.rightNode);

        Node newRoot = new Node(root.data);
        newRoot.leftNode = leftNode;
        newRoot.rightNode = rightNode;

        return newRoot;
    }

    public static void main(String[] args) {
        Node root = createBinaryTree();

        BinaryTree binaryTree = new BinaryTree();
        // 遍历树
        System.out.print("recursion: ");
        binaryTree.recursion(root);
        // 叶子节点数目
        System.out.println();
        binaryTree.caculateLeafCount(root);
        System.out.println("leafCount: "+binaryTree.leafCount);
        // 计算树的高度
        System.out.println("caculateLevelCount: "+binaryTree.caculateLevelCount(root));
        // 拷贝（释放）
        Node newRoot = binaryTree.copyBinaryTree(root);
        System.out.println("newRoot:");
        System.out.print("recursion: ");
        binaryTree.recursion(root);

    }

}
