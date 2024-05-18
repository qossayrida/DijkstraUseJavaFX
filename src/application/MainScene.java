package application;

import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.geometry.*;         
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.text.*;
import javafx.scene.text.Font;


// Main scene class that represents the initial user interface.
public class MainScene extends Scene {

    // Constructor for the MainScene class.
    public MainScene(Stage primaryStage){
        super(new StackPane(), Screen.getPrimary().getVisualBounds().getWidth(), Screen.getPrimary().getVisualBounds().getHeight());
        StackPane layout = (StackPane) getRoot();
        layout.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        layout.getStyleClass().add("root");
                
        
        // Set up the main layout using a VBox.
        VBox vBox = new VBox(250);
        vBox.setPadding(new Insets(60));
        vBox.setAlignment(Pos.TOP_CENTER);
    	
        
    	// Set up a background image for the scene.
        Image backgroundImage = new Image("file:data\\4.jpg");
        ImageView backgroundImageView = new ImageView(backgroundImage);
        backgroundImageView.fitWidthProperty().bind(layout.widthProperty());
        backgroundImageView.fitHeightProperty().bind(layout.heightProperty());

        // Set up a welcome label.
        Label welcomeLabel = new Label("Welcome");
        welcomeLabel.setFont(Font.font("Century Gothic", FontWeight.BOLD, 40));
        welcomeLabel.setStyle("-fx-text-fill: #000000;");

        // Array of button labels.
        String[] strings = {
            "Choose data manule",
            "Gaza Map",
            "Palestine Map"
        };
        
        // ArrayList to store buttons.
        Button[] buttons = new Button [strings.length]; 
        
        // Method to set up buttons with labels and actions.
        setupButtons(strings, buttons, primaryStage);
        
        // Set up a VBox to arrange buttons vertically.
        HBox ArrangementButtons = new HBox(100);
        ArrangementButtons.setAlignment(Pos.TOP_CENTER);
        ArrangementButtons.getChildren().addAll(buttons);
        
        // Add components to the main layout.
        vBox.getChildren().addAll(welcomeLabel, ArrangementButtons);
        layout.getChildren().addAll(backgroundImageView, vBox);
    }
    
    // This method sets up buttons based on an array of strings and adds them to a stage in a JavaFX application.
    public static void setupButtons(String[] strings, Button[] buttons, Stage primaryStage) {
        // Iterate over the array of strings to create buttons
        for (int i = 0; i < strings.length; i++) {
            Button button = new Button(strings[i]); // Create a new button with text from the strings array
            buttons[i]=button; // Add the button to the buttons ArrayList
            button.getStyleClass().add("custom-button"); // Add a custom CSS class for styling
            button.setPrefHeight(50); // Set preferred height for the button
            button.setPrefWidth(250); // Set preferred width for the button
        }

        // Set an action for the first button to open a file chooser for file selection
        buttons[0].setOnAction(e -> {
        	 SceneManager.setScene(new LoadDataScene(primaryStage));
        });
        
        buttons[1].setOnAction(e -> {
        	Graph graph = SceneManager.readGraph("data\\citiesForGaza.xlsx","data\\mapDimensionsForGaza.xlsx","data\\GazaMap.jpg");
            SceneManager.setScene(new ResultScene(graph));
        });
        
        buttons[2].setOnAction(e -> {
        	Graph graph = SceneManager.readGraph("data\\citiesForPalestine.xlsx","data\\mapDimensionsForPalestine.xlsx","data\\PalestineMap.jpg");
            SceneManager.setScene(new ResultScene(graph));
        });
        
        ImageView gazaIconImageView = new ImageView(new Image("file:data\\gazaIcon.png"));
        gazaIconImageView.setFitWidth(32);  // Set the desired width
        gazaIconImageView.setFitHeight(32); // Set the desired height
        buttons[1].setGraphic(gazaIconImageView);
        buttons[1].setContentDisplay(ContentDisplay.RIGHT);
        
        ImageView palestineIconImageView = new ImageView(new Image("file:data\\palestineIcon.png"));
        palestineIconImageView.setFitWidth(20);  // Set the desired width
        palestineIconImageView.setFitHeight(32); // Set the desired height
        buttons[2].setGraphic(palestineIconImageView);
        buttons[2].setContentDisplay(ContentDisplay.RIGHT);
    }
    
    
}
