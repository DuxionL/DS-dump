import com.datastruct.*;

class Student {
    private int nim;
    private String nama;

    Student(int nim, String nama) {
        /* 
         * penggunaan 'this' digunakan untuk membedakan antara
         * variabel dari class dan parameter pada constructor
        */
        this.nim = nim;
        this.nama = nama;
    }

    @Override //toString dari class String
    /*
     * agar method CetakList() dari class TheArrayList 
     * dapat mencetak nim dan nama dari setiap object class ini.
    */
    public String toString() {
        return(Integer.toString(nim) + " - " + nama + " ");
    }
}

//Main Method
public class Probing {
    public static void main(String[] args) {
        LinearProbing<Object,String> T = new LinearProbing<Object,String>(7); 
         
        T.put(2, "B");
        T.put(10, "J");
        T.put(14, "N");
        T.put(19, "S");
        T.put(24, "X");
        T.put(23, "W");
        T.displayHashTable();

        if (T.get(23) != null) System.out.println("Data found: " + T.get(23));
        else System.out.println("data does not exist!");
    }
}
