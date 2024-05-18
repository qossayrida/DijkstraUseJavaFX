package application;

import javafx.scene.control.*;

class Vertex {
    String cityName; // Name of the city
    double longitude; // Longitude of the city
    double latitude; // Latitude of the city
    Edge adjacencyList; // Adjacency list for the edges connected to the vertex
    boolean street; // Indicates if the vertex represents a street
    boolean known; // Indicates if the vertex is known during graph traversal
    double dist; // Distance value used in graph algorithms
    int path; // Path information for graph traversal
    HeapNode heapNode; // Node used in heap for graph algorithms
    Label cityNameLabel; // Label for displaying the city name in the UI
    RadioButton radioButton; // RadioButton for city selection in the UI


    public Vertex(String cityName, double latitude, double longitude, boolean street) {
        this.cityName = cityName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.street = street;
        this.adjacencyList = null;
        this.cityNameLabel = new Label(cityName); // Initialize UI label
        this.radioButton = new RadioButton(); // Initialize UI radio button
    }

    /**
     * Adds an edge to the vertex's adjacency list.
     */
    public void addEdge(Vertex city) {
        Edge newEdge = new Edge(city);
        newEdge.next = this.adjacencyList;
        this.adjacencyList = newEdge;
    }

    /**
     * Resets the vertex attributes for graph algorithms.
     */
    public void reset() {
        this.known = false;
        this.dist = Graph.INT_MAX;
        this.path = Graph.NOT_VERTEX;
        this.heapNode = null;
    }

    /**
     * Gets the name of the city.
     */
    public String getCityName() {
        return cityName;
    }

    /**
     * Gets the longitude of the city.
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * Gets the latitude of the city.
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * Checks if the vertex represents a street.
     */
    public boolean isStreet() {
        return street;
    }
}
