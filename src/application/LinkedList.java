package application;

class LinkedList {
    private LinkedListNode head; // Reference to the head of the linked list

    // Constructor to initialize an empty linked list
    public LinkedList() {
        head = null;
    }

    // Method to add a new vertex to the linked list
    public void addVertex(Vertex vertex) {
    	LinkedListNode newNode = new LinkedListNode(vertex);
        if (head == null) {
            // If the list is empty, set the new node as the head
            head = newNode;
        } else {
            // Otherwise, add the new node to the end of the list
        	LinkedListNode current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
    }
    
    public LinkedListNode getHead() {
		return head;
	}
}
