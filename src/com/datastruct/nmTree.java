package com.datastruct;
/*
 * Modified from: http://www.java2s.com/ref/java/java-data-structures-234-tree.html
 * 
*/

/*
Kelas TI B Kelompok 6 GENAP
-	535250061 : Lulu Lydia Andrean
-	535250077 : Garry Malvin Jiu
-	535250093 : Jessica Jeslyn Sutanto
-	535250096 : Chatrina Citra Patricia Hutabarat
 */
class DataItem<K, V> {
   private K key;
   private V Data;

   public DataItem(K key, V Data) {
      this.key = key;
      this.Data = Data;
   }

   public void setKey(K key) {
      this.key = key;
   }

   public void setData(V Data) {
      this.Data = Data;
   }

   public K getKey() {
      return key;
   }

   public V getData() {
      return Data;
   }

   public void displayItem() {
      System.out.print("/" + key + ": " + Data);
   }
}

class nmNode<K, V> {
   private static final int ORDER = 4; // 2-3 Tree, ORDER = 4
   private int numItems;
   private nmNode<K, V> parent;
   @SuppressWarnings("unchecked") //kena type safety
   private nmNode<K, V> childArray[] = (nmNode<K, V>[]) new nmNode[ORDER];
   @SuppressWarnings("unchecked")
   private DataItem<K, V> itemArray[] = (DataItem<K, V>[]) new DataItem[ORDER - 1];

   int compareKeys(K a, K b) {
      return String.valueOf(a).compareTo(String.valueOf(b));
   }
   
   public void connectChild(int childNum, nmNode<K, V> child) {
      childArray[childNum] = child;
      if (child != null)
         child.parent = this;
   }

   public nmNode<K, V> disconnectChild(int childNum) {
      nmNode<K, V> tempnmNode = childArray[childNum];
      childArray[childNum] = null;
      return tempnmNode;
   }

   public nmNode<K, V> getChild(int childNum) {
      return childArray[childNum];
   }

   public nmNode<K, V> getParent() {
      return parent;
   }

   public boolean isLeaf() {
      return (childArray[0] == null) ? true : false;
   }

   public int getNumItems() {
      return numItems;
   }

   public DataItem<K, V> getItem(int index) // get DataItem at index
   {
      return itemArray[index];
   }

   public boolean isFull() {
      return (numItems == ORDER - 1) ? true : false;
   }

   public int findItem(K key)
   {
      for (int j = 0; j < ORDER - 1; j++) 
      {
         if (itemArray[j] == null) 
            break;
         else if (compareKeys(itemArray[j].getKey(), key) == 0)
            return j;
      }
      return -1;
   }

   // re-arrange items in a node after disconnect a child
   public void deleteItem(K key)
   {
      for (int j = 0; j < numItems; j++) 
      {
         if (itemArray[j] == null) 
            break;
         else if (compareKeys(itemArray[j].getKey(), key) == 0) {
            for (int k = j; k < numItems; k++) {
               itemArray[k] = itemArray[k+1];
            }
            --numItems;
            break;
         }
      }
   }

   public int insertItem(DataItem<K, V> newItem) {
      numItems++;
      K newKey = newItem.getKey();

      for (int j = ORDER - 2; j >= 0; j--) {
         if (itemArray[j] == null)
            continue;
         else {
            K itsKey = itemArray[j].getKey();
            if (compareKeys(newKey, itsKey) < 0)
               itemArray[j + 1] = itemArray[j];
            else {
               itemArray[j + 1] = newItem;
               return j + 1;
            }
         }
      }
      itemArray[0] = newItem;
      return 0;
   }

   public DataItem<K, V> removeItem() {
      DataItem<K, V> temp = itemArray[numItems - 1];
      itemArray[numItems - 1] = null;
      numItems--;
      return temp;
   }

   public void displaynmNode() {
      for (int j = 0; j < numItems; j++)
         itemArray[j].displayItem();
      System.out.println("/");
   }

}

public class nmTree<K, V> {
   private nmNode<K, V> root = new nmNode<>();

   public V find(K key) {
      nmNode<K, V> curnmNode = root;
      int childNumber;
      while (true) {
         if ((childNumber = curnmNode.findItem(key)) != -1)
            return curnmNode.getItem(childNumber).getData(); 
         else if (curnmNode.isLeaf())
            return null; 
         else
            curnmNode = getNextChild(curnmNode, key);
      } 
   }

   public void insert(K key, V Data) {
      nmNode<K, V> curnmNode = root;
      DataItem<K, V> tempItem = new DataItem<>(key, Data);
      
      while (true) {
         if (curnmNode.isLeaf())
            break;
         else
            curnmNode = getNextChild(curnmNode, key);
      }
      curnmNode.insertItem(tempItem);
      // dilanggar dahulu max items per nmNode dari 2 menjadi 3 
      if (curnmNode.isFull()) 
      {
        split(curnmNode); // split it
        curnmNode = curnmNode.getParent();
        // untuk setiap nmNode yang melanggar max items per nmNode
        while(curnmNode != null && curnmNode.isFull()){
            split(curnmNode); // split it
            curnmNode = curnmNode.getParent();
        }
      }
   }

   public nmNode<K, V> adoptMerge(nmNode<K, V> curNode, int index) {
      boolean left = true;
      nmNode<K, V> parentNode = curNode.getParent();
      int numItems = parentNode.getNumItems();
      int j = 0; nmNode<K, V> siblingNode = null;
      int curPos = 0;
      while(j < numItems && parentNode.getChild(j) != curNode) {
         siblingNode = parentNode.getChild(j);
         ++j;
         curPos = j;
      }
      if(j == 0) {
         siblingNode = parentNode.getChild(j+1);
         left = false;
      }
      //select right sibling if it has more items than the left with single item
      if(siblingNode.getNumItems() == 1 && j < numItems) {
         if(parentNode.getChild(j+1).getNumItems() > 1) {
            siblingNode = parentNode.getChild(j+1);
            left = false;
         }
      }
      //adopt
      if(siblingNode.getNumItems() > 1) {
         //left sibling
         if(left) {
            curNode.getItem(index).setKey(parentNode.getItem(j-1).getKey());
            curNode.getItem(index).setData(parentNode.getItem(j-1).getData());
            parentNode.getItem(j-1).setKey(siblingNode.getItem(siblingNode.getNumItems()-1).getKey());
            parentNode.getItem(j-1).setData(siblingNode.getItem(siblingNode.getNumItems()-1).getData());
            if(curNode.isLeaf() == false) {
               nmNode<K, V> lastNode = siblingNode.disconnectChild(siblingNode.getNumItems());
               nmNode<K, V> tempNode = curNode.disconnectChild(0);
               curNode.connectChild(1, tempNode);
               curNode.connectChild(0, lastNode);
            }
            siblingNode.removeItem();
         }
         else {
            //right sibling
            curNode.getItem(index).setKey(parentNode.getItem(j).getKey());
            curNode.getItem(index).setData(parentNode.getItem(j).getData());
            parentNode.getItem(j).setKey(siblingNode.getItem(0).getKey());
            parentNode.getItem(j).setData(siblingNode.getItem(0).getData());
            if(curNode.isLeaf() == false) {
               nmNode<K, V> firstNode = siblingNode.disconnectChild(0);
               nmNode<K, V> tempNode = curNode.disconnectChild(curNode.getNumItems());
               curNode.connectChild(0, tempNode);
               curNode.connectChild(curNode.getNumItems(), firstNode);
            }
            siblingNode.deleteItem(siblingNode.getItem(0).getKey());
         }
      }
      //merge
      else {
         if(parentNode.getNumItems() == 1) {
            DataItem<K, V> tempItem = new DataItem<>(parentNode.getItem(0).getKey(), parentNode.getItem(0).getData());
            siblingNode.insertItem(tempItem);
            if(parentNode == root) {
               return root;   
            }
            //merge or adopt upward
            else {
               curNode = parentNode;
               return (adoptMerge(curNode, 0));
            }
         }
         else {
            DataItem<K, V> tempItem = null;
            if(curPos > 0) tempItem = new DataItem<>(parentNode.getItem(curPos-1).getKey(), parentNode.getItem(curPos-1).getData());
            else tempItem = new DataItem<>(parentNode.getItem(curPos).getKey(), parentNode.getItem(curPos).getData());
            siblingNode.insertItem(tempItem);
            if(left) {
               for(j=curPos; j < parentNode.getNumItems(); ++j) {
                  nmNode<K, V> tempNode = parentNode.disconnectChild(j+1);
                  parentNode.connectChild(j, tempNode);
               }
               parentNode.deleteItem(tempItem.getKey());
            }
            else {
               for(j=0; j < parentNode.getNumItems(); ++j) {
                  nmNode<K, V> tempNode = parentNode.disconnectChild(j+1);
                  parentNode.connectChild(j, tempNode);
               }
               parentNode.deleteItem(tempItem.getKey());
            }
         }
      }
      return root;
   }

   //Delete a key from a leaf node in nmTree 
   public void delete(K key) {
      nmNode<K, V> curNode = root;

      while (true) {
         if (curNode.isLeaf())
            break;
         else
            curNode = getNextChild(curNode, key);
      }
      //delete leaf node
      if(curNode.isLeaf()) {
         int index = curNode.findItem(key);
         if(index == -1) {
            return;
         }
         if(curNode.getNumItems() > 1)
            curNode.deleteItem(key);
         else {
            root = adoptMerge(curNode, index);
         }   
      }
      //delete internal node
   }   

   public void split(nmNode<K, V> thisnmNode) {
      DataItem<K, V> itemB, itemC;
      nmNode<K, V> parent, child2, child3;
      int itemIndex;

      itemC = thisnmNode.removeItem();
      itemB = thisnmNode.removeItem();
      child2 = thisnmNode.disconnectChild(2);
      child3 = thisnmNode.disconnectChild(3);
      
      nmNode<K, V> newRight = new nmNode<>();

      if (thisnmNode == root) {
         root = new nmNode<>();
         parent = root;
         root.connectChild(0, thisnmNode);
      } else
         parent = thisnmNode.getParent();

      itemIndex = parent.insertItem(itemB);
      int n = parent.getNumItems();

      for (int j = n - 1; j > itemIndex; j--) {
         nmNode<K, V> temp = parent.disconnectChild(j);
         parent.connectChild(j + 1, temp);
      }
      parent.connectChild(itemIndex + 1, newRight);
      newRight.insertItem(itemC);
      newRight.connectChild(0, child2);
      newRight.connectChild(1, child3);
   }

   public nmNode<K, V> getNextChild(nmNode<K, V> thenmNode, K theData) {
      int j;

      int numItems = thenmNode.getNumItems();
      for (j = 0; j < numItems; j++) {
         if (thenmNode.compareKeys(theData, thenmNode.getItem(j).getKey()) < 0)
            return thenmNode.getChild(j);
      }
      return thenmNode.getChild(j);
   }

   public void displayTree() {
      recDisplayTree(root, 0, 0);
   }

   private void recDisplayTree(nmNode<K, V> thisnmNode, int level, int childNumber) {
      System.out.print("level=" + level + " child=" + childNumber + " ");
      thisnmNode.displaynmNode(); 

      int numItems = thisnmNode.getNumItems();
      for (int j = 0; j < numItems + 1; j++) {
         nmNode<K, V> nextnmNode = thisnmNode.getChild(j);
         if (nextnmNode != null)
            recDisplayTree(nextnmNode, level + 1, j);
         else
            return;
      }
   }

}
