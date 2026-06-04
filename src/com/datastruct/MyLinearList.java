package com.datastruct;
/*
 * Learning Single Linear / Linked List
 * head -> |10|o->|20|o->|30| <-o tail
 * 
 * @author: Lely Hiryanto
 */

class Node<T> {
    /*
     * class untuk mendefinisikan satu node di linkedlist secara generic
     * [data | next]   
     * data = bagian node yang berisi informasi (teks, bilangan, object of a class)
     * next = bagian node yang berisi alamat (pointer) ke node lainnya
     */

    private T data;
    private Node<T> next;

    //constructor
    Node(T value) {
        data = value;
        next = null;
    }

    //setter
    public void setData(T data) {
        this.data = data;
        next = null;
    }
    public void setNext(Node<T> next) {
        this.next = next;
    }

    //getter
    public T getData() {
        return data;
    }
    public Node<T> getNext() {
        return next;
    }
}

public class MyLinearList<T> {
    Node<T> head;
    Node<T> tail;

    //constructor
    public MyLinearList() {
        head = null;
        tail = null;
    }

    /*
     * Method pushQ, yang akan meletakkan setiap node baru yang dibuat di akhir list
     * (queue)
     */
    public void pushQ(T value) {
        //buat node baru
		Node<T> newNode = new Node<T>(value);
        //jika list kosong, head dan tail sama-sama menunjuk ke node pertama
		if (head == null) {
            head = newNode;
            tail = newNode;
        }
        //jika tidak kosong, tail diupdate untuk menunjuk ke node baru
		else {
			tail.setNext(newNode);
            tail = newNode;
		}
	}

    /*
     * Method pushS, yang akan meletakkan setiap node baru yang dibuat di awal list
     * (stack)
     */
    public void pushS(T value) {
        //buat node baru
		Node<T> newNode = new Node<T>(value);
        //jika list kosong, head dan tail sama-sama menunjuk ke node pertama
		if (head == null) {
			head = newNode;
			tail = newNode;
		}
        //jika tidak kosong, node baru menunjuk ke node pertama di list
        //head diupdate untuk menunjuk ke node baru
		else {
            newNode.setNext(head);
			head = newNode;
		}
	}

    /*
     * Mencetak bagian informasi dari setiap node di linkedlist
     */
    public void cetakList() {
        //set pointer curr untuk menunjuk ke node pertama (node pertama yang ditunjuk oleh head)
		Node<T> curr = head;
        //jika list kosong, tampilkan pesan list kosong
		if(curr == null) System.out.println("List kosong!");
        /*
         * jika list tidak kosong, maka cetak bagian informasi 
           dari setiap node yang dikunjungi. 
        */
		else {
            System.out.print("[ ");
			while(curr != null) {
				System.out.print(curr.getData().toString() + " ");
                /*
                 * curr diupdate untuk menunjuk ke node selanjutnya
                 * sampai curr null, dimana curr diset ke bagian 'next' 
                 * dari node terakhir yang bernilai null. Artinya, 
                 * pointer curr sudah ada di akhir list.
                 */
				curr = curr.getNext();
			}
		}
        System.out.println("]");
	}


    //pop(), remove first element untuk queue dan stack
    private Node<T> pop() {
        Node<T> n;
        if(head == null) n = null;
        else {
            n = head;
            head = head.getNext();
            if (head == null) {
                tail = null;
            }   
        }
        return n;
    }

    //return bagian data dari node
    public T remove() {
        Node<T> n = pop();
        if(n == null) return null;
        else return n.getData();
    }

    //@overloading method remove()
    public boolean remove(T data) {
        Node<T> curr = head;
        Node<T> prev = head;
        boolean deleted = false;

        while(curr != null && !deleted) {
            // jika setiap elemen dari data sama
            // dengan data yang dicari
            if(curr.getData().equals(data)) {
                deleted = true;
                //node sebelum dilink ke node berikut dari node yang diremove
                prev.setNext(curr.getNext()); 
                //jika node pertama yang diremove, update head ke node kedua
                if(curr == head) head = head.getNext();
                //jika list hanya memiliki satu nod, set head = null (list kosong)         
                if(head == null) tail = null;
            }
            //jika belum ditemukan node yang akan diremove
            //simpan pointer ke node saat ini, dan update
            //pointer ke node berikutnya
            else {
                prev = curr;
                curr = curr.getNext();
            }
        }

        return deleted;
    }

    //mengecek apakah list kosong atau tidak
    public boolean isEmpty() {
        if(head == null) return true;
        else return false;
    }
}



