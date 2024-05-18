package application;

import javafx.scene.image.*;

class Graph {
    public HashTable verticesTable;
    public double [] mapDimensions;
    ImageView mapImageView;
    Heap heap;
    int cityNumber;
    private double mapWidth; 
    private double mapHeight;
    
    public static final int INT_MAX = Integer.MAX_VALUE;
    public static final int NOT_VERTEX = -1;
    
    public Graph(int initialCapacity,double [] MapDimensions,ImageView mapImageView) {
        this.mapDimensions = MapDimensions;
        this.mapImageView = mapImageView;
        verticesTable = new HashTable(initialCapacity);
    }

    public void addVertex(String cityName, double latitude, double longitude, boolean street) {
    	if(!street)
    		cityNumber++;
        Vertex newVertex = new Vertex(cityName, latitude, longitude, street);
        verticesTable.put(newVertex);
    }


    public void addEdge(String city1, String city2) {
    	Vertex source=findVertexOfCity(city1),destination=findVertexOfCity(city2);
        if (source !=null && destination!= null) {
        	source.addEdge(destination);
        } else {
            SceneManager.showAlert("Error", city2 + " or "+city1 +"not added as vertex");
        } 
    }
    
    public Vertex findVertexOfCity(String cityName) {
        return verticesTable.getVertex(cityName);
    }

    
    public static double findDistance(Vertex source, Vertex destination) {
        // Convert the latitudes and longitudes from degrees to radians.
        double lat1Rad = Math.toRadians(source.latitude);
        double lon1Rad = Math.toRadians(source.longitude);
        double lat2Rad = Math.toRadians(destination.latitude);
        double lon2Rad = Math.toRadians(destination.longitude);

        double deltaLat = lat2Rad - lat1Rad;
        double deltaLon = lon2Rad - lon1Rad;
        double a = Math.sin(deltaLat / 2) * Math.sin(deltaLat / 2) +
                   Math.cos(lat1Rad) * Math.cos(lat2Rad) *
                   Math.sin(deltaLon / 2) * Math.sin(deltaLon / 2);
        return 2.0 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a)) * 6371.0 ;
        
    }
    
    ////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                          			 Dijkstra Algorithm                                   //
    ////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////
    
    public void dijkstra(Vertex source, Vertex destination) {
        initializeHeap(source);

        while (!heap.isEmpty()) {
            int v = heap.removeMin().vertex;
            Vertex I = verticesTable.getTable()[v];
                
            if (I == destination) break;         
            I.known = true;

            Edge current = I.adjacencyList;
            while (current != null) {
                int w = verticesTable.getVertexIndex(current.city.getCityName());
                Vertex II = verticesTable.getTable()[w];
                
                if (!II.known) {
                    double cvw = findDistance(I, current.city);
                    if (I.dist + cvw < II.dist) {
                    	II.dist = I.dist + cvw;
                    	II.path = v;
                        heap.decreaseKey(w,II.dist); // Updated to use vertex index
                    }
                }
                
                current = current.next;
            }
        }
    }

    private void initializeHeap(Vertex start) {
        heap = new Heap(verticesTable.getTableSize()); 

        for (int i = 0; i < verticesTable.getTableSize(); i++) {
        	verticesTable.getTable()[i].reset();
        	verticesTable.getTable()[i].heapNode = new HeapNode(i, Double.MAX_VALUE);
        	verticesTable.getTable()[i].dist = Double.MAX_VALUE;
            heap.insert(verticesTable.getTable()[i].heapNode);
        }

        int startIndex = verticesTable.getVertexIndex(start.cityName);
        if (startIndex != -1) {
        	verticesTable.getTable()[startIndex].dist = 0;
            heap.decreaseKey(startIndex, 0); // Updated to use vertex index
        }
    }

    public void buildPath(Vertex vertex, StringBuilder pathBuilder, LinkedList pathList) throws Exception {
        if (vertex.dist == Graph.INT_MAX) {
            throw new Exception("No path exists to vertex " + vertex.getCityName());
        }

        if (vertex.path != Graph.NOT_VERTEX) {
            Vertex previousVertex = verticesTable.getTable()[vertex.path];
            pathList.addVertex(vertex); // Add the current vertex to the path list
            buildPath(previousVertex, pathBuilder, pathList); // Recursive call to build previous vertices in the path
            pathBuilder.append(" -- ").append(String.format("%.2f", vertex.dist - previousVertex.dist) + " km -->\n");
        } else {
            // If there's no previous vertex, add the current vertex as the start of the path
            pathList.addVertex(vertex);
        }

        pathBuilder.append(vertex.getCityName());
    }
    
    ////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                           Draw Pin for each City in the map                                //
    ////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////
    
    public double longitudeToX(double longitude) {
        double minLongitude = mapDimensions[0];
        double maxLongitude = mapDimensions[1];
        return (longitude - minLongitude) / (maxLongitude - minLongitude) * mapWidth;
    }
    
    public double latitudeToY(double latitude) {
        double minLatitude = mapDimensions[2] ;
        double maxLatitude = mapDimensions[3] ;
        return (maxLatitude - latitude) / (maxLatitude - minLatitude) * mapHeight;
    }
    
    
    ////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////
    //                                      Getter & Setter                                       //
    ////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////
    
	public double getMapWidth() {
		return mapWidth;
	}

	public void setMapWidth(double mapWidth) {
		this.mapWidth = mapWidth;
	}

	public double getMapHeight() {
		return mapHeight;
	}

	public void setMapHeight(double mapHeight) {
		this.mapHeight = mapHeight;
	}

	public String[] getCityNames(){
    	String[] cityNamesArray = new String[cityNumber] ;
    	int counter=0;
        for (int i = 0; i < verticesTable.getTableSize(); i++)
        	if(!verticesTable.getTable()[i].isStreet())
        		cityNamesArray[counter++]=verticesTable.getTable()[i].getCityName();
        return cityNamesArray;
    }
}