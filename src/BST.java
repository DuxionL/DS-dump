import com.datastruct.*;

public class BST {
    public static void main(String[] args) {
        BinarySearchTree<Integer,String> bst = new BinarySearchTree<Integer,String>();
        bst.insert(40, "A");bst.insert(20, "B");
        bst.insert(50, "C");bst.insert(10, "D");
        bst.insert(30, "E");bst.insert(60, "F");
        bst.insert(5, "G");bst.insert(25, "H");
        bst.insert(35, "I");
        System.out.print("Levelorder: ");
        bst.levelOrder();
        System.out.println();System.out.println("60 => " + bst.search(60));
        System.out.println(bst.min());
        System.out.println(bst.max());bst.delete(40);
        System.out.print("Levelorder: ");
        bst.levelOrder();
        System.out.println();
    }
        
}
