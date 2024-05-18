package application;


class Heap {
    private HeapNode[] heapArray; // Array to store heap elements
    private int maxSize; // Maximum size of the heap
    private int size; // Current size of the heap
    private int[] positions; // To keep track of positions of vertices in the heap

    /**
     * Constructs a new heap with the specified maximum size.
     */
    public Heap(int maxSize) {
        this.maxSize = maxSize;
        this.heapArray = new HeapNode[maxSize + 1]; // Index 0 is not used
        this.size = 0;
        this.positions = new int[maxSize];
        for (int i = 0; i < maxSize; i++) {
            positions[i] = -1; // Initialize positions array
        }
        heapArray[0] = new HeapNode(-1, Double.MIN_VALUE); // Dummy node at index 0
    }

    /**
     * Swaps the elements at the specified positions in the heap.
     */
    private void swap(int fpos, int spos) {
        HeapNode tmp = heapArray[fpos];
        heapArray[fpos] = heapArray[spos];
        heapArray[spos] = tmp;
        positions[heapArray[fpos].vertex] = fpos; // Update positions array
        positions[heapArray[spos].vertex] = spos; // Update positions array
    }

    /**
     * Maintains the min-heap property starting at the given position.
     */
    private void minHeapify(int pos) {
        if (!isLeaf(pos)) {
            int left = leftChild(pos);
            int right = rightChild(pos);
            int smallest = pos;

            if (left <= size && heapArray[left].key < heapArray[smallest].key) {
                smallest = left;
            }

            if (right <= size && heapArray[right].key < heapArray[smallest].key) {
                smallest = right;
            }

            if (smallest != pos) {
                swap(pos, smallest);
                minHeapify(smallest);
            }
        }
    }

    /**
     * Inserts a new element into the heap.
     */
    public void insert(HeapNode element) {
        if (size >= maxSize) {
            throw new IllegalStateException("Heap is full");
        }

        heapArray[++size] = element;
        positions[element.vertex] = size;

        int current = size;
        while (heapArray[current].key < heapArray[parent(current)].key) {
            swap(current, parent(current));
            current = parent(current);
        }
    }

    /**
     * Decreases the key of a vertex in the heap and maintains the min-heap property.
     */
    public void decreaseKey(int vertex, double newKey) {
        int pos = positions[vertex];
        if (pos == -1 || pos > size) {
            return; // Vertex not in heap
        }

        heapArray[pos].key = newKey;
        while (pos > 1 && heapArray[pos].key < heapArray[parent(pos)].key) {
            swap(pos, parent(pos));
            pos = parent(pos);
        }
    }

    /**
     * Removes and returns the element with the minimum key from the heap.
     */
    public HeapNode removeMin() {
        if (isEmpty()) {
            return null;
        }

        HeapNode popped = heapArray[1];
        heapArray[1] = heapArray[size];
        positions[heapArray[1].vertex] = 1; // Update positions array
        size--;
        minHeapify(1);
        return popped;
    }

    /**
     * Checks if the heap is empty.
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Calculates the index of the parent of the given position.
     */
    private int parent(int pos) {
        return pos / 2;
    }

    /**
     * Calculates the index of the left child of the given position.
     */
    private int leftChild(int pos) {
        return 2 * pos;
    }

    /**
     * Calculates the index of the right child of the given position.
     */
    private int rightChild(int pos) {
        return 2 * pos + 1;
    }

    /**
     * Checks if the given position is a leaf node in the heap.
     */
    private boolean isLeaf(int pos) {
        return pos > (size / 2) && pos <= size;
    }
}
