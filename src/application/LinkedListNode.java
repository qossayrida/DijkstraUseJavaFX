package application;


public class LinkedListNode {
	 Vertex vertex; // Data associated with the node
	 LinkedListNode next; // Reference to the next node in the list

     public LinkedListNode(Vertex vertex) {
         this.vertex = vertex;
         this.next = null;
     }

	public Vertex getVertex() {
		return vertex;
	}
}
