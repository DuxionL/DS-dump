import com.datastruct.*;

public class HeapSort {
    public static void main(String[] args) {
        //Test Hash Table and Heap Sort
        Integer[] kunci = {11,2,30,4,15};
        String[] data = {"Andree", "Leana", "Faviola", "Loyce", "Quincy"};
        //set priority = false for heap max; sebaliknya untuk heap min
        Heap<Integer, String> heap = new Heap<Integer, String>(kunci.length, false);
        for(int i = 0; i < kunci.length; i++) 
            heap.add(kunci[i], data[i]);
        heap.buildHeap();
        heap.display();
        System.out.println(heap.getData(heap.removeFirst()));
        heap.display();
        heap.insert(3, "Lely");
        heap.display();
        heap.sort();
        heap.display();
    }
            
}
