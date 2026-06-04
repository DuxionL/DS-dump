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


import com.datastruct.nmTree;

public class Tree23Main {
   public static void main(String[] args) {
      nmTree<Object, Object> theTree = new nmTree<>();
      theTree.insert(24, "twenty_four");
      theTree.insert("41", 41);
      theTree.insert(28, 28.0f);
      theTree.insert(53, 53);
      theTree.insert("85", 85.0f);
      theTree.insert(57, "fiftyseven");
      theTree.insert(13, 13);
      theTree.insert("B31", 31.0f);
      theTree.insert(74, "seventyfour");
      theTree.insert(27, 27);
      theTree.insert("X72", 72.0f);
      theTree.insert(50, "Fiddy");
      theTree.insert(48, 48);
      theTree.insert("five", "five");
      theTree.insert(19, 19.0f);
      theTree.insert(97, 97);
      theTree.insert("59k", "fiftynine");
      theTree.insert("sixteen", 16);
      theTree.insert(90, 90.0f);
      theTree.insert(75, "seventyfive");
      theTree.insert(18, 18);
      theTree.displayTree();
      
      Object found = theTree.find("five");
      if (found != null)
         System.out.println("Key 'five' ditemukan di tree dengan data: " + found);
      else
         System.out.println("Key 'five' tidak ditemukan di tree");

      theTree.delete("59k");
      System.out.println("2-3 tree after delete:");
      theTree.displayTree();
   }
}

/*
output:
level=0 child=0 /53: 53/
level=1 child=0 /28: 28.0/
level=2 child=0 /18: 18/24: twenty_four/
level=3 child=0 /13: 13/
level=3 child=1 /19: 19.0/
level=3 child=2 /27: 27/
level=2 child=1 /48: 48/
level=3 child=0 /41: 41/
level=3 child=1 /50: Fiddy/
level=1 child=1 /85: 85.0/
level=2 child=0 /59k: fiftynine/
level=3 child=0 /57: fiftyseven/
level=3 child=1 /74: seventyfour/75: seventyfive/
level=2 child=1 /97: 97/X72: 72.0/
level=3 child=0 /90: 90.0/
level=3 child=1 /B31: 31.0/
level=3 child=2 /five: five/sixteen: 16/
Key 'five' ditemukan di tree dengan data: five
2-3 tree after delete:
level=0 child=0 /53: 53/
level=1 child=0 /28: 28.0/
level=2 child=0 /18: 18/24: twenty_four/
level=3 child=0 /13: 13/
level=3 child=1 /19: 19.0/
level=3 child=2 /27: 27/
level=2 child=1 /48: 48/
level=3 child=0 /41: 41/
level=3 child=1 /50: Fiddy/
level=1 child=1 /85: 85.0/
level=2 child=0 /59k: fiftynine/
level=3 child=0 /57: fiftyseven/
level=3 child=1 /74: seventyfour/75: seventyfive/
level=2 child=1 /97: 97/X72: 72.0/
level=3 child=0 /90: 90.0/
level=3 child=1 /B31: 31.0/
level=3 child=2 /five: five/sixteen: 16/
*/