package com.datastruct;
/*
 * Create Simple Binary Search Tree
 * 
 * @author: Lely Hiryanto
 * 
 */

 class BTNode<K,V> {
    private K key;    //key ada bilangan bulat
    private V data;   // object data dari sebuah class
    private BTNode<K,V> llink; //left link
    private BTNode<K,V> rlink; //right link

    //constructor
    public BTNode(K k, V data) {
        this.key = k; 
        this.data = data;   
        this.llink = null;  
        this.rlink = null;  
    }

    public void setKey(K key) {
        this.key = key;
    }
    public K getKey() {
        return key;
    }
    public void setData(V data) {
        this.data = data;
    }
    public V getData() {
        return data;
    }
    public void setLlink(BTNode<K, V> llink) {
        this.llink = llink;
    }
    public BTNode<K, V> getLlink() {
        return llink;
    }
    public void setRlink(BTNode<K, V> rlink) {
        this.rlink = rlink;
    }
    public BTNode<K, V> getRlink() {
        return rlink;
    }
}

public class BinaryTree<K, V>{

    //rekursif in order traversal
    public void printInOrder(BTNode<K,V> node) {
        //T sebelumnya adalah child node
        if(node == null) return;
        else {
            //ke left node secara rekursif
            printInOrder(node.getLlink());
            //cetak key dari node
            System.out.print(node.getKey() + ":" + node.getData() + " ");
            //ke right node secara rekursif
            printInOrder(node.getRlink());
        }
    }

    //post order traversal
    public void printPostOrder(BTNode<K,V> node) {
        //T sebelumnya adalah child node
        if(node == null) return;
        else {
            //ke left node secara rekursif
            printPostOrder(node.getLlink());
            //ke right node secara rekursif
            printPostOrder(node.getRlink());
            //cetak key dari node 
            System.out.print(node.getKey() + ":" + node.getData() + " ");
        }
    }  

    //pre order traversal
    void printPreOrder(BTNode<K,V> node) {
        //T sebelumnya adalah child node
        if(node == null) return;
        else {
            //cetak key dari node 
            System.out.print(node.getKey() + ":" + node.getData() + " ");
            //ke left node secara rekursif
            printPreOrder(node.getLlink());
            //ke right node secara rekursif
            printPreOrder(node.getRlink());
        }
    }  
    
    //cetak node di setiap level dari root, left child, 
    //dan right child secara rekursif 
    private void printLevelOrderRec(MyLinearList<BTNode<K,V>> q) {
        if(q.isEmpty()) return;
        BTNode<K,V> node = q.remove();
        //cetak key dari node 
        System.out.print(node.getKey() + ":" + node.getData() + " ");
        if(node.getLlink() != null) q.pushQ(node.getLlink());
        if(node.getRlink() != null) q.pushQ(node.getRlink());
        printLevelOrderRec(q);
    }

    //level order traversal
    public void printLevelOrder(BTNode<K,V> node) {
        //buat queue untuk menampung node disetiap level
        MyLinearList<BTNode<K,V>> q = new MyLinearList<BTNode<K,V>>();
        q.pushQ(node);
        //memanggil fungsi rekursif untuk mencetak key 
        //dari node di setiap leve
        printLevelOrderRec(q);
    }
}

