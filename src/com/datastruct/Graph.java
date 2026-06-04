/*
Kelas TI B Kelompok 6 GENAP
-	535250061 : Lulu Lydia Andrean
-	535250077 : Garry Malvin Jiu
-	535250093 : Jessica Jeslyn Sutanto
-	535250096 : Chatrina Citra Patricia Hutabarat
 */
package com.datastruct;
/* 
 * Struktur data Graph dengan bobot pada setiap edge
 * sources: https://www.lavivienpost.net/weighted-graph-as-adjacency-list/  
 * 
 */

import java.util.HashMap;
import java.util.Map;

class Edge<T> { 
	T vertex;           
	private T neighbor;  //connected vertex
	private int weight;  //weight

	// Constructor, Time O(1) Space O(1)
	public Edge(T u, T v, int w) {
		this.vertex   = u;
		this.neighbor = v;
		this.weight   = w;
	}

	public void setVertex(T vertex) {
		this.vertex = vertex;
	}
	public T getVertex() {
		return vertex;
	}
	public void setNeighbor(T neighbor) {
		this.neighbor = neighbor;
	}
	public T getNeighbor() {
		return neighbor;
	}
	public void setWeight(int weight) {
		this.weight = weight;
	}
	public int getWeight() {
		return weight;
	}

	@Override
	public String toString() {
		return "(" + vertex + "," + neighbor + "," + weight + ")";
	}
}

// Helper buat Dijkstra
class DijkstraEntry<T> {
	T vertex;
	int dist;

	DijkstraEntry(T vertex, int dist) {
		this.vertex = vertex;
		this.dist   = dist;
	}
}

public class Graph<T> {
	Map<T, MyLinearList<Edge<T>>> adj;
	boolean directed;

	// Constructor, Time O(1) Space O(1)
	public Graph(boolean type) {
		adj      = new HashMap<>();
		directed = type; // false: undirected, true: directed
	}

	// Add edges including adding nodes, Time O(1) Space O(1)
	public void addEdge(T a, T b, int w) {
		adj.putIfAbsent(a, new MyLinearList<>());
		adj.putIfAbsent(b, new MyLinearList<>());
		adj.get(a).pushQ(new Edge<>(a, b, w)); // edge dari a ke b
		if (!directed) {
			adj.get(b).pushQ(new Edge<>(b, a, w)); // edge dari b ke a (undirected)
		}
	}

	// Print graph as hashmap, Time O(V+E), Space O(1)
	public void printGraph() {
		for (T key : adj.keySet()) {
			System.out.print(key.toString() + " : ");
			Node<Edge<T>> curr = adj.get(key).head;
			while (curr != null) {
				System.out.print(curr.getData());
				curr = curr.getNext();
			}
			System.out.println();
		}
	}

	// DFS, Time O(V+E), Space O(V)
	public void DFS(T src) {
		if (!adj.containsKey(src)) return;
		HashMap<T, Boolean> visited = new HashMap<>();
		helperDFS(src, visited);
		System.out.println();
	}

	private void helperDFS(T v, HashMap<T, Boolean> visited) {
		visited.put(v, true);
		System.out.print(v.toString() + " ");
		Node<Edge<T>> node = adj.get(v).head;
		while (node != null) {
			T u = node.getData().getNeighbor();
			if (visited.get(u) == null)
				helperDFS(u, visited);
			node = node.getNext();
		}
	}

	// BFS, Time O(V+E), Space O(V)
	public void BFS(T src) {
		if (!adj.containsKey(src)) return;
		MyLinearList<T> q = new MyLinearList<>();
		HashMap<T, Boolean> visited = new HashMap<>();
		q.pushQ(src);
		visited.put(src, true);
		while (!q.isEmpty()) {
			T v = q.remove();
			System.out.print(v.toString() + " ");
			Node<Edge<T>> node = adj.get(v).head;
			while (node != null) {
				T u = node.getData().getNeighbor();
				if (visited.get(u) == null) {
					q.pushQ(u);
					visited.put(u, true);
				}
				node = node.getNext();
			}
		}
		System.out.println();
	}

	public void deleteEdge(T a, T b) {
		if (!adj.containsKey(a) || !adj.containsKey(b)) return;

		MyLinearList<Edge<T>> listA = adj.get(a);
		Node<Edge<T>> curr = listA.head;
		while (curr != null) {
			if (curr.getData().getNeighbor().equals(b)) {
				listA.remove(curr.getData());
				break;
			}
			curr = curr.getNext();
		}

		if (!directed) {
			MyLinearList<Edge<T>> listB = adj.get(b);
			curr = listB.head;
			while (curr != null) {
				if (curr.getData().getNeighbor().equals(a)) {
					listB.remove(curr.getData());
					break;
				}
				curr = curr.getNext();
			}
			if (listB.isEmpty()) adj.remove(b);
		}

		if (listA.isEmpty()) adj.remove(a);
	}

	//p11 dijkstra (nama susah bet)
	public Map<T, Integer> dijkstra(T start, Map<T, T> prev) {
		Map<T, Integer> res = new HashMap<>();
		MyLinearList<DijkstraEntry<T>> pq = new MyLinearList<>();
		for (T key : adj.keySet()) {
			res.put(key, Integer.MAX_VALUE);
			prev.put(key, null);
		}
		res.put(start, 0);
		pq.pushQ(new DijkstraEntry<>(start, 0));
		while (!pq.isEmpty()) {
			T u = pq.remove().vertex;
			if (adj.get(u) == null) continue;
			Node<Edge<T>> node = adj.get(u).head;
			while (node != null) {
				T v = node.getData().getNeighbor();
				int weight = node.getData().getWeight();
				if (res.get(v) > res.get(u) + weight) {
					res.put(v, res.get(u) + weight);
					prev.put(v, u);
					DijkstraEntry<T> newEntry = new DijkstraEntry<>(v, res.get(v));
					if (pq.isEmpty() || pq.head.getData().dist >= newEntry.dist) {
						pq.pushS(newEntry);
					} else {
						Node<DijkstraEntry<T>> curr = pq.head;
						while (curr.getNext() != null && curr.getNext().getData().dist < newEntry.dist) {
							curr = curr.getNext();
						}
						Node<DijkstraEntry<T>> newNode = new Node<>(newEntry);
						newNode.setNext(curr.getNext());
						curr.setNext(newNode);
						if (newNode.getNext() == null) pq.tail = newNode;
					}
				}
				node = node.getNext();
			}
		}
		return res;
	}

	//p12 Kruskal , pake heap.java di package ds
	public MyLinearList<Edge<T>> kruskal() {
		MyLinearList<Edge<T>> mst = new MyLinearList<>();
		MyLinearList<String> seen = new MyLinearList<>();
		int edgeCount = 0;

		for (T u : adj.keySet()) {
			Node<Edge<T>> node = adj.get(u).head;
			while (node != null) {
				T v   = node.getData().getNeighbor();
				String keyUV = u.toString().compareTo(v.toString()) < 0
						? u + "-" + v
						: v + "-" + u;

				boolean alreadySeen = false;
				Node<String> s = seen.head;
				while (s != null) {
					if (s.getData().equals(keyUV)) { alreadySeen = true; break; }
					s = s.getNext();
				}
				if (!alreadySeen) { seen.pushQ(keyUV); edgeCount++; }
				node = node.getNext();
			}
		}

		Heap<Integer, Edge<T>> minHeap = new Heap<>(edgeCount, true);
		seen = new MyLinearList<>();

		for (T u : adj.keySet()) {
			Node<Edge<T>> node = adj.get(u).head;
			while (node != null) {
				T v   = node.getData().getNeighbor();
				int w = node.getData().getWeight();
				String keyUV = u.toString().compareTo(v.toString()) < 0
						? u + "-" + v
						: v + "-" + u;

				boolean alreadySeen = false;
				Node<String> s = seen.head;
				while (s != null) {
					if (s.getData().equals(keyUV)) { alreadySeen = true; break; }
					s = s.getNext();
				}
				if (!alreadySeen) {
					T fromV = u.toString().compareTo(v.toString()) < 0 ? u : v;
					T toV   = u.toString().compareTo(v.toString()) < 0 ? v : u;
					minHeap.add(w, new Edge<>(fromV, toV, w));
					seen.pushQ(keyUV);
				}
				node = node.getNext();
			}
		}


		minHeap.sort();

		HashMap<T, T> parent = new HashMap<>();
		for (T key : adj.keySet()) parent.put(key, key);

		int size = minHeap.size();
		for (int i = size - 1; i >= 0; i--) {
			Edge<T> e= minHeap.getData(i);
			T rootFrom= find(parent, e.getVertex());
			T rootTo= find(parent, e.getNeighbor());

			if (!rootFrom.equals(rootTo)) {
				mst.pushQ(e);
				union(parent, rootFrom, rootTo);
			}
		}

		return mst;
	}

	private T find(HashMap<T, T> parent, T v) {
		if (!parent.get(v).equals(v))
			parent.put(v, find(parent, parent.get(v)));
		return parent.get(v);
	}
	private void union(HashMap<T, T> parent, T a, T b) {
		parent.put(a, b);
	}
	public void printKruskalMST() {
		MyLinearList<Edge<T>> mst = kruskal();
		System.out.print("MST dengan Algoritma Kruskal:[ ");
		int total = 0;
		Node<Edge<T>> curr = mst.head;
		while (curr != null) {
			Edge<T> e = curr.getData();
			System.out.print("(" + e.getVertex() + "," + e.getNeighbor() + "," + e.getWeight() + ") ");
			total += e.getWeight();
			curr = curr.getNext();
		}
		System.out.println("]");
		System.out.println("MST Length = " + total);
	}

	public void topologicalSort() {
		Map<T, Integer> inDegree = new HashMap<>();
		for (T vertex : adj.keySet()) {
			inDegree.put(vertex, 0);
		}

		for (T vertex : adj.keySet()) {
			MyLinearList<Edge<T>> neighbors = adj.get(vertex);
			if (neighbors != null) {
				Node<Edge<T>> current = neighbors.head;
				while (current != null) {
					T neighbor = current.getData().getNeighbor();

					// Tambahkan nilai indegree neighbor +1
					inDegree.put(neighbor, inDegree.get(neighbor) + 1);

					current = current.getNext();
				}
			}
		}

		MyLinearList<T> queue = new MyLinearList<>();
		for (T vertex : inDegree.keySet()) {
			if (inDegree.get(vertex) == 0) {

				// Masukkan vertex ke queue
				queue.pushQ(vertex);
				
			}
		}

		MyLinearList<T> topOrder = new MyLinearList<>();
		while (!queue.isEmpty()) {
			T current = queue.remove();

			// Masukkan current ke topOrder
			topOrder.pushQ(current);

			MyLinearList<Edge<T>> neighbors = adj.get(current);
			if (neighbors != null) {
				Node<Edge<T>> node = neighbors.head;
				while (node != null) {
					T neighbor = node.getData().getNeighbor();

					// Kurangi indegree neighbor sebanyak 1
					inDegree.put(neighbor, inDegree.get(neighbor) - 1);

					if (inDegree.get(neighbor) == 0) {

						// Masukkan neighbor ke queue
						queue.pushQ(neighbor);

					}
					node = node.getNext();
				}
			}
		}

		System.out.print("Topological Ordering: ");
		topOrder.cetakList();
	}
}