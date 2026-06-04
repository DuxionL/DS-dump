/*
Kelas TI B Kelompok 6 GENAP
-	535250061 : Lulu Lydia Andrean
-	535250077 : Garry Malvin Jiu
-	535250093 : Jessica Jeslyn Sutanto
-	535250096 : Chatrina Citra Patricia Hutabarat
 */
import com.datastruct.*;

class MyVertex {
	String nodeName;
	MyVertex(String name) {
		this.nodeName = name;
	}

	@Override
	public String toString() {
		return nodeName;
	}
}

public class GraphMain {
	public static void main(String[] args) {
		MyVertex v1 = new MyVertex("v1");
		MyVertex v2 = new MyVertex("v2");
		MyVertex v3 = new MyVertex("v3");
		MyVertex v4 = new MyVertex("v4");
		MyVertex v5 = new MyVertex("v5");
		MyVertex v6 = new MyVertex("v6");
		MyVertex v7 = new MyVertex("v7");
		MyVertex v8 = new MyVertex("v8");

		Graph<MyVertex> UG = new Graph<>(false); // undirected

		UG.addEdge(v1, v3, 10);
		UG.addEdge(v1, v4, 14);
		UG.addEdge(v1, v2, 22);
		UG.addEdge(v2, v5,  5);
		UG.addEdge(v2, v4, 15);
		UG.addEdge(v3, v6, 12);
		UG.addEdge(v3, v7, 18);
		UG.addEdge(v4, v5,  8);
		UG.addEdge(v4, v7,  7);
		UG.addEdge(v5, v8,  6);
		UG.addEdge(v6, v7, 15);
		UG.addEdge(v7, v8, 17);

		System.out.println("Undirected Graph:");
		UG.printGraph();

		System.out.println();
		UG.printKruskalMST();

		System.out.println("Topological Sort");
        MyVertex a = new MyVertex("a");
        MyVertex b = new MyVertex("b");
        MyVertex c = new MyVertex("c");
        MyVertex d = new MyVertex("d");
        MyVertex e = new MyVertex("e");
 
        Graph<MyVertex> WG = new Graph<MyVertex>(true); // directed
       
        WG.addEdge(b, c, 1);
        WG.addEdge(b, d, 2);
        WG.addEdge(a, b, 4);
        WG.addEdge(a, c, 6);
        WG.addEdge(d, e, 2);
        WG.addEdge(e, c, 1);

		System.out.println("Weighted Graph (WG):");
		WG.printGraph();

		System.out.println();
		WG.topologicalSort();
	}
}
/*
p12 expected output
PS C:\Users\Blob\Downloads\mejepe\Pertemuan11_Kelompok6\mjp\MyJavaProject>  & 'C:\Program Files\Zulu\zulu-25\bin\java.exe' '-XX:+ShowCodeDetailsInExceptionMessages' '-cp' 'C:\Users\Blob\Downloads\mejepe\Pertemuan11_Kelompok6\mjp\MyJavaProject\bin' 'GraphMain' 
Undirected Graph:
v2 : (v2,v1,22)(v2,v5,5)(v2,v4,15)
v4 : (v4,v1,14)(v4,v2,15)(v4,v5,8)(v4,v7,7)
v5 : (v5,v2,5)(v5,v4,8)(v5,v8,6)
v6 : (v6,v3,12)(v6,v7,15)
v7 : (v7,v3,18)(v7,v4,7)(v7,v6,15)(v7,v8,17)
v3 : (v3,v1,10)(v3,v6,12)(v3,v7,18)
v8 : (v8,v5,6)(v8,v7,17)
v1 : (v1,v3,10)(v1,v4,14)(v1,v2,22)

MST dengan Algoritma Kruskal:
[ (v2,v5,5) (v5,v8,6) (v4,v7,7) (v4,v5,8) (v1,v3,10) (v3,v6,12) (v1,v4,14) ]
MST Length = 62 

p13 expected output
Topological Sort
Weighted Graph (WG):
c : 
d : (d,e,2)
e : (e,c,1)
a : (a,b,4)(a,c,6)
b : (b,c,1)(b,d,2)

Topological Ordering: [ a b d e c ]
*/