package application;

class Edge {
	Vertex city;
    Edge next;

    public Edge(Vertex city) {
        this.city = city;
        this.next = null;
    }
}
