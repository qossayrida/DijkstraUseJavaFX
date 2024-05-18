package application;

import javafx.collections.FXCollections;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Screen;

public class ResultScene extends Scene {
	
	// Declaration of instance variables
	Graph graph;
	ComboBox<String> sourceComboBox;
	ComboBox<String> destinationComboBox;
	TextField distanceField;
	TextArea pathArea;
	Pane labelPane, pinPane, linesPane;
	private int selectionState = 0;
	private RadioButton[] selectionRadioButton = new RadioButton[2];

	// Constructor for the ResultScene class
	public ResultScene(Graph graph) {
	    // Creating a new Scene with a StackPane as the root, and setting its dimensions to the screen size
	    super(new StackPane(), Screen.getPrimary().getVisualBounds().getWidth(), Screen.getPrimary().getVisualBounds().getHeight());
	    StackPane layout = (StackPane) getRoot();
	    layout.getStylesheets().add(getClass().getResource("application.css").toExternalForm()); // Adding CSS for styling
	    layout.getStyleClass().add("root");
	    this.graph = graph;

	    // Creating the main GridPane for layout
	    GridPane gridPane = new GridPane();
	    gridPane.setHgap(10); // Horizontal gap between columns
	    gridPane.setVgap(30); // Vertical gap between rows
	    gridPane.setAlignment(Pos.CENTER);

	    // Creating ComboBoxes for source and destination cities
	    String[] cityNamesArray = graph.getCityNames();
	    sourceComboBox = new ComboBox<>(FXCollections.observableArrayList(cityNamesArray));
	    destinationComboBox = new ComboBox<>(FXCollections.observableArrayList(cityNamesArray));
	    sourceComboBox.getStyleClass().add("custom-ComboBox");
	    destinationComboBox.getStyleClass().add("custom-ComboBox");

	    // Form elements for distance and path information
	    distanceField = new TextField();
	    distanceField.setEditable(false);
	    distanceField.getStyleClass().add("text-field");
	    pathArea = new TextArea();
	    pathArea.setEditable(false);
	    pathArea.getStyleClass().add("text-area");

	    // Labels for form elements
	    Label sourceLabel = new Label("Source:");
	    // Setting font style for labels
	    sourceLabel.setFont(Font.font("Arial", FontWeight.BOLD, 15));
	    Label destinationLabel = new Label("Destination:");
	    destinationLabel.setFont(Font.font("Arial", FontWeight.BOLD, 15));
	    Label distanceLabel = new Label("Distance:");
	    distanceLabel.setFont(Font.font("Arial", FontWeight.BOLD, 15));
	    Label pathLabel = new Label("Path:");
	    pathLabel.setFont(Font.font("Arial", FontWeight.BOLD, 15));

	    // Adding form elements to the GridPane
	    gridPane.addRow(0, sourceLabel, sourceComboBox);
	    gridPane.addRow(1, destinationLabel, destinationComboBox);
	    gridPane.addRow(2, distanceLabel, distanceField);
	    gridPane.addRow(3, pathLabel, pathArea);
	    GridPane.setColumnSpan(pathArea, 2);

	    // Event handling for source ComboBox selection
	    sourceComboBox.setOnAction(event -> {
	        // Handling the selection of a source city
	        String selected = sourceComboBox.getValue();
	        if (selected != null) {
	            Vertex vertex = graph.findVertexOfCity(selected);
	            createImageView("file:data\\start.png", vertex.radioButton);
	            destinationComboBox.setValue(null);

	            // Clearing previous selections and creating a new pin for the source city
	            if (selectionRadioButton[0] != null) {
	                linesPane.getChildren().clear();
	                createImageView("file:data\\mapPin.png", selectionRadioButton[0]);
	                selectionRadioButton[0] = null;
	            }
	            if (selectionRadioButton[1] != null && selectionRadioButton[1] != vertex.radioButton)
	                createImageView("file:data\\mapPin.png", selectionRadioButton[1]);

	            if (selectionRadioButton[1] != null)
	                selectionRadioButton[1] = null;

	            selectionRadioButton[0] = vertex.radioButton;
	            selectionState = 1;
	        }
	    });

	    // Event handling for destination ComboBox selection
	    destinationComboBox.setOnAction(event -> {
	        // Handling the selection of a destination city
	        String selected = destinationComboBox.getValue();
	        if (selected != null) {
	            RadioButton radioButton = graph.findVertexOfCity(selected).radioButton;

	            // Clearing previous selections and creating a new pin for the destination city
	            if (selectionRadioButton[1] != null) {
	                linesPane.getChildren().clear();
	                createImageView("file:data\\mapPin.png", selectionRadioButton[1]);
	                selectionRadioButton[1] = null;
	            }

	            createImageView("file:data\\end.png", radioButton);
	            selectionRadioButton[1] = radioButton;
	            selectionState = 0;
	        }
	    });

	    // String array for button labels
	    String[] strings = {
	            "Find",
	            "Show all Roads",
	            "Clear",
	            "Back to main page"
	    };

	    // Array of Buttons for the specified labels
	    Button[] buttons = new Button[strings.length];
	    // Method call to set up buttons
	    setupButtons(strings, buttons);
	    // Creating a VBox for buttons
	    VBox buttonBox = new VBox(30, buttons);
	    buttonBox.setAlignment(Pos.CENTER);
	    GridPane.setHalignment(buttonBox, HPos.LEFT);
	    gridPane.add(buttonBox, 0, 4, 3, 1);

	    // Creating background image and Panes for map display
	    ImageView background = new ImageView(new Image("file:data\\4.jpg"));
	    background.fitWidthProperty().bind(layout.widthProperty());
	    background.fitHeightProperty().bind(layout.heightProperty());

	    // Creating a Pane for displaying lines on the map
	    linesPane = new Pane();

	    // Creating a StackPane for map display with scroll functionality
	    StackPane stackPane = new StackPane(graph.mapImageView, linesPane);
	    ScrollPane mapScrollPane = new ScrollPane(stackPane);
	    mapScrollPane.getStylesheets().add("file:file.css");
	    mapScrollPane.setMaxHeight(700);
	    mapScrollPane.setMaxWidth(700);
	    mapScrollPane.setPannable(true);

	    // Setting up map dimensions based on contentBounds
	    Bounds contentBounds = stackPane.getBoundsInLocal();
	    graph.setMapHeight(contentBounds.getHeight());
	    graph.setMapWidth(contentBounds.getWidth());

	    // Creating Panes for city labels and pins
	    pinPane = new Pane();
	    labelPane = new Pane();
	    // Method call to set up city radio buttons
	    setupCityRadioButtons();

	    // Adding Panes for labels and pins to the main StackPane
	    stackPane.getChildren().add(labelPane);
	    stackPane.getChildren().add(pinPane);

	    // Creating an HBox for organizing the layout
	    HBox hBox = new HBox(30, mapScrollPane, gridPane);
	    hBox.setAlignment(Pos.CENTER);
	    // Adding background, layout, and HBox to the main layout
	    layout.getChildren().addAll(background, hBox);
	}

    
    
	/**
	 * Sets up buttons with specified labels and configurations.
	 */
	public void setupButtons(String[] strings, Button[] buttons) {
	    // Loop through each string in the 'strings' array to create and configure buttons
	    for (int i = 0; i < strings.length; i++) {
	        Button button = new Button(strings[i]); // Create a new button with label from the strings array
	        buttons[i] = button; // Store the button in the 'buttons' array
	        button.getStyleClass().add("custom-button"); // Add a CSS class for styling
	        button.setPrefHeight(40); // Set the preferred height for the button
	        button.setPrefWidth(250); // Set the preferred width for the button
	    }

	    // Action for the first button (Find)
	    buttons[0].setOnAction(e -> {
	        try {
	            linesPane.getChildren().clear();
	            String source = sourceComboBox.getValue(), destination = destinationComboBox.getValue();
	            if (source != null && destination != null) {
	            	Vertex sourceVertex = graph.findVertexOfCity(source),destinationVertex = graph.findVertexOfCity(destination);
	                graph.dijkstra(sourceVertex,destinationVertex); 
	                
	                if (destinationVertex.dist == Double.MAX_VALUE)
	                    throw new Exception();
	                
	                StringBuilder pathBuilder=new StringBuilder(); 
	                LinkedList pathList=new LinkedList();
	                graph.buildPath(destinationVertex,pathBuilder,pathList);
	                pathArea.setText(pathBuilder.toString());
	                distanceField.setText(String.format("%.2f", destinationVertex.dist)+ " km");
	                drawPath(pathList);
	            } else {
	                throw new Exception();
	            }
	        } catch (Exception e2) {
	            distanceField.setText("∞");
	            pathArea.setText("There is no path to follow");
	        }
	    });

	    // Action for the second button (Show all Roads)
	    buttons[1].setOnAction(e -> {
	        linesPane.getChildren().clear();
	        if (selectionRadioButton[0] != null) {
	            linesPane.getChildren().clear();
	            createImageView("file:data\\mapPin.png", selectionRadioButton[0]);
	            selectionRadioButton[0] = null;
	        }
	        if (selectionRadioButton[1] != null) {
	            createImageView("file:data\\mapPin.png", selectionRadioButton[1]);
	            selectionRadioButton[1] = null;
	        }
	        drawAllRoads(linesPane);
	    });

	    // Action for the third button (Clear)
	    buttons[2].setOnAction(e -> {
	        linesPane.getChildren().clear();
	        selectionState = 0;
	        sourceComboBox.setValue(null);
	        destinationComboBox.setValue(null);
	        distanceField.setText("");
	        pathArea.setText("");
	        if (selectionRadioButton[0] != null) {
	            linesPane.getChildren().clear();
	            createImageView("file:data\\mapPin.png", selectionRadioButton[0]);
	            selectionRadioButton[0] = null;
	        }
	        if (selectionRadioButton[1] != null) {
	            createImageView("file:data\\mapPin.png", selectionRadioButton[1]);
	            selectionRadioButton[1] = null;
	        }
	    });

	    // Action for the fourth button (Back to main page)
	    buttons[3].setOnAction(e -> {
	        SceneManager.setMainScene();
	    });
	}

    
	/**
	 * Sets up radio buttons and labels for each city in the graph.
	 * Radio buttons represent cities, and labels display city names.
	 */
	public void setupCityRadioButtons() {
	    // Loop through each vertex in the graph
	    for (Vertex vertex : graph.verticesTable.getTable()) {
	        // Check if the vertex is not representing a street
	        if (!vertex.isStreet()) {
	            RadioButton rb = vertex.radioButton; // Get the radio button associated with the vertex
	            Label cityNameLabel = vertex.cityNameLabel; // Get the label associated with the vertex

	            // Set layout for the radio button based on longitude and latitude
	            rb.setLayoutX(graph.longitudeToX(vertex.getLongitude()) - 10);
	            rb.setLayoutY(graph.latitudeToY(vertex.getLatitude()) - 15);
	            rb.getStyleClass().add("custom-radiobutton"); // Add a CSS class for styling
	            createImageView("file:data\\mapPin.png", rb); // Create and display a pin image for the city

	            // Set font and layout for the city name label
	            cityNameLabel.setFont(Font.font("Century Gothic", 8));
	            cityNameLabel.setLayoutX(graph.longitudeToX(vertex.getLongitude()) - 10);
	            cityNameLabel.setLayoutY(graph.latitudeToY(vertex.getLatitude()) + 4);

	            // Event handling for the radio button selection
	            rb.setOnAction(e -> {
	                if (selectionState == 0)
	                    sourceComboBox.setValue(vertex.cityName);
	                else
	                    destinationComboBox.setValue(vertex.cityName);
	            });

	            // Add the radio button and city name label to the corresponding panes
	            pinPane.getChildren().add(rb);
	            labelPane.getChildren().add(cityNameLabel);
	        } else {
	            // If the vertex represents a street, display a dot image
	            ImageView dotImageView = new ImageView(new Image("file:data\\dot.png"));
	            dotImageView.setLayoutX(graph.longitudeToX(vertex.getLongitude()));
	            dotImageView.setLayoutY(graph.latitudeToY(vertex.getLatitude()));
	            pinPane.getChildren().add(dotImageView);
	        }
	    }
	}

    
	/**
	 * Draws all roads (edges) connecting cities on the map.
	 * Clears existing selections and UI elements related to paths.
	 */
	public void drawAllRoads(Pane mapPane) {
	    linesPane.getChildren().clear();
	    selectionState = 0;
	    sourceComboBox.setValue(null);
	    destinationComboBox.setValue(null);
	    distanceField.setText("");
	    pathArea.setText("");
	    
	    // Clear previous pin selections
	    if (selectionRadioButton[0] != null) {
	        linesPane.getChildren().clear();
	        createImageView("file:data\\mapPin.png", selectionRadioButton[0]);
	        selectionRadioButton[0] = null;
	    }
	    if (selectionRadioButton[1] != null) {
	        createImageView("file:data\\mapPin.png", selectionRadioButton[0]);
	        selectionRadioButton[1] = null;
	    }

	    // Iterate over all vertices in the graph
	    for (Vertex vertex : graph.verticesTable.getTable()) {
	        // Check if the vertex has adjacent cities
	        if (vertex.adjacencyList != null) {
	            Edge currentEdge = vertex.adjacencyList;
	            
	            // Iterate over all edges for each vertex
	            while (currentEdge != null) {
	                double startX = graph.longitudeToX(vertex.getLongitude()) + 2;
	                double startY = graph.latitudeToY(vertex.getLatitude()) + 2;
	                double endX = graph.longitudeToX(currentEdge.city.getLongitude()) + 2;
	                double endY = graph.latitudeToY(currentEdge.city.getLatitude()) + 2;
	                double midX = (startX + endX) / 2;
	                double midY = (startY + endY) / 2;

	                Line line = new Line(startX, startY, endX, endY);
	                Path arrowHead = new Path();

	                // Size of the arrowhead
	                double arrowHeadSize = 5;
	                // Angle of the line
	                double angle = Math.atan2(midY - startY, midX - startX) - Math.PI / 2.0;
	                double sin = Math.sin(angle);
	                double cos = Math.cos(angle);
	                // Calculate the points for the arrowhead
	                double x1 = (-1 / 2.0 * cos + Math.sqrt(3) / 2 * sin) * arrowHeadSize + midX;
	                double y1 = (-1 / 2.0 * sin - Math.sqrt(3) / 2 * cos) * arrowHeadSize + midY;
	                double x2 = (1 / 2.0 * cos + Math.sqrt(3) / 2 * sin) * arrowHeadSize + midX;
	                double y2 = (1 / 2.0 * sin - Math.sqrt(3) / 2 * cos) * arrowHeadSize + midY;

	                arrowHead.getElements().add(new MoveTo(midX, midY));
	                arrowHead.getElements().add(new LineTo(x1, y1));
	                arrowHead.getElements().add(new LineTo(x2, y2));
	                arrowHead.getElements().add(new LineTo(midX, midY));

	                line.setStroke(Color.BLACK);
	                arrowHead.setFill(Color.BLACK);
	                arrowHead.setStroke(Color.BLACK);
	                mapPane.getChildren().add(line);
	                mapPane.getChildren().add(arrowHead);

	                currentEdge = currentEdge.next;
	            }
	        }
	    }
	}
    
	/**
	 * Draws the path (edges) connecting cities for a specific path in red on the map.
	 */
	public void drawPath(LinkedList pathVertices) {
		LinkedListNode currentNode = pathVertices.getHead();

	    // Check if the linked list is not null and has more than one vertex to connect with lines
	    if (currentNode != null && currentNode.next != null) {
	        // Iterate over each pair of consecutive vertices in the path
	        while (currentNode != null && currentNode.next != null) {
	            Vertex to = currentNode.vertex;
	            Vertex from = currentNode.next.vertex;
	            double startX = graph.longitudeToX(from.getLongitude()) + 2;
	            double startY = graph.latitudeToY(from.getLatitude()) + 2;
	            double endX = graph.longitudeToX(to.getLongitude()) + 2;
	            double endY = graph.latitudeToY(to.getLatitude()) + 2;
	            double midX = (startX + endX) / 2;
	            double midY = (startY + endY) / 2;

	            // Create a line representing the edge between consecutive vertices
	            Line line = new Line(startX, startY, endX, endY);
	            Path arrowHead = new Path();

	            // Size of the arrowhead
	            double arrowHeadSize = 5;
	            // Angle of the line
	            double angle = Math.atan2(midY - startY, midX - startX) - Math.PI / 2.0;
	            double sin = Math.sin(angle);
	            double cos = Math.cos(angle);
	            // Calculate the points for the arrowhead
	            double x1 = (-1 / 2.0 * cos + Math.sqrt(3) / 2 * sin) * arrowHeadSize + midX;
	            double y1 = (-1 / 2.0 * sin - Math.sqrt(3) / 2 * cos) * arrowHeadSize + midY;
	            double x2 = (1 / 2.0 * cos + Math.sqrt(3) / 2 * sin) * arrowHeadSize + midX;
	            double y2 = (1 / 2.0 * sin - Math.sqrt(3) / 2 * cos) * arrowHeadSize + midY;

	            arrowHead.getElements().add(new MoveTo(midX, midY));
	            arrowHead.getElements().add(new LineTo(x1, y1));
	            arrowHead.getElements().add(new LineTo(x2, y2));
	            arrowHead.getElements().add(new LineTo(midX, midY));

	            // Set properties for the line and arrowhead
	            line.setStroke(Color.RED);
	            arrowHead.setFill(Color.RED);
	            arrowHead.setStroke(Color.RED);
	            line.setStrokeWidth(2); // Set the line width

	            // Add the line and arrowhead to the linesPane
	            linesPane.getChildren().add(line);
	            linesPane.getChildren().add(arrowHead);

	            currentNode = currentNode.next;
	        }
	    }
	}


    
    private void createImageView(String path,RadioButton button) {
    	ImageView image = new ImageView(new Image(path));
    	image.setPreserveRatio(true);
        button.setGraphic(image);
    }
}

