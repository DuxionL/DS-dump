package com.datastruct;

public class BinarySearchTree<K extends Comparable<? super K>,V> 
                        extends BinaryTree<K,V> 
                        implements Tree<K,V>
{
    
    private BTNode<K,V> root;

    public BinarySearchTree() {
        root = null;
    }

    public void insert(K key, V data) {
        root = insertNode(root, key, data);
    }

    public void delete(K key) {
        root = deleteNode(root, key);
    }

    public V search(K key) {
        V info = null;
        info = getData(find(root,key));
        return info;
    }

    public K max() {
        K kunci = null;
        kunci = getKey(findMax(root));
        return kunci;
    }
    
    public K min() {
        K kunci = null;
        kunci = getKey(findMin(root));
        return kunci;
    }

    public void inOrder() {
        printInOrder(root);
    }

    public void preOrder() {
        printPreOrder(root);
    }

    public void postOrder() {
        printPostOrder(root);
    }

    public void levelOrder() {
        printLevelOrder(root);
    }
    
    public K getKey(BTNode<K,V> node) {
        return node.getKey();
    }

    public V getData(BTNode<K,V> node) {
        return node.getData();
    }

    /* 
    public void cetakNode(BTNode<K,V> node) {
        System.out.print(node);
    }
    */

    private BTNode<K,V> insertNode(BTNode<K,V> node, K k, V data) {
        //Jika tree masih kosong (belum ada node sama sekali), atau
        //T sebelumnnya adalah child node
        //Buat node baru yang akan dilink-kan ke child node sebelumnya
        if(node == null) {
            //buat node baru
		    BTNode<K,V> newNode = new BTNode<K,V>(k, data);
            return newNode;
        }
        //key dari node baru lebih kecil dari key child node sebelumnya
        //go to the left node (subtree)
        //node baru akan dilink ke left link
        else if(k.compareTo(node.getKey()) < 0) {
            node.setLlink(insertNode(node.getLlink(), k, data));
            return node;
        }
        //key dari node baru lebih besar dari ata sama dengan 
        //key child node sebelumnya
        //go to the right node (subtree)
        //node baru akan dilink ke right link
        else {
            node.setRlink(insertNode(node.getRlink(), k, data));
            return node;
        }
    }

    private BTNode<K,V> deleteNode(BTNode<K,V> node, K k) {
        if(node == null) return node;
        //jika key dari node yang dicari lebih besar 
        //dari key node yang dikunjungi, telusuri ke kanan
        else if(node.getKey().compareTo(k) < 0) {
            node.setRlink(deleteNode(node.getRlink(),k));
            return node;
        }
        //jika key dari node yang dicari lebih kecil 
        //dari key node yang dikunjungi, telusuri ke kiri
        else if(node.getKey().compareTo(k) > 0){
            node.setLlink(deleteNode(node.getLlink(), k));
            return node;
        }
        //node yang akan dihapus ditemukan
        //jika memiliki child node di kanan
        if(node.getLlink() == null) {
            BTNode<K,V> temp = node.getRlink();
            return temp;
        }
        //jika memiliki child node di kiri
        else if(node.getRlink() == null) {
            BTNode<K,V> temp = node.getLlink();
            return temp;
        }
        //jika memiliki child node di kiri dan kanan
        else {
            //cari node dengan key terbesar dari subtree kiri
            BTNode<K,V> parent = node; 
            BTNode<K,V> child = node.getLlink();
            while (child.getRlink() != null) {
                parent = child;
                child = child.getRlink();
            }
            //jika ditemukan node dengan key terbesar dari subtree kiri
            if(parent != node) parent.setRlink(child.getLlink());
            //jika tidak ditemukan node dengan key terbesar dari subtree kiri
            else parent.setLlink(child.getLlink());

            //copy data dari dengan key terbesar ke node yang akan dihapus
            node.setKey(child.getKey());
            node.setData(child.getData());

            return node;
        }
    }

    private BTNode<K,V> find(BTNode<K,V> node, K k) {
        //node adalah subtree (root dari subtree)
        if(node == null || node.getKey() == k) return node;
        else if(node.getKey().compareTo(k) < 0) return find(node.getRlink(), k);
        else return find(node.getLlink(), k);
    }

    private BTNode<K,V> findMin(BTNode<K,V> node) {
        if(node == null || node.getLlink() == null) return node;
        else {
            return findMin(node.getLlink());
        }
    }

    private BTNode<K,V> findMax(BTNode<K,V> node) {
        if(node == null || node.getRlink() == null) return node;
        else {
            return findMax(node.getRlink());
        }
    }

}
