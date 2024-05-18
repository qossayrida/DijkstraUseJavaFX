package application;

import java.io.File;

import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class LoadDataScene extends Scene {
	
	String citiesFile=null;
	String mapDimensionsFile=null;
	String mapPath=null;
	
	public LoadDataScene(Stage primaryStage) {
        super(new StackPane(), Screen.getPrimary().getVisualBounds().getWidth(), Screen.getPrimary().getVisualBounds().getHeight());
        StackPane layout = (StackPane) getRoot();
        layout.getStylesheets().add(getClass().getResource("application.css").toExternalForm()); // Adding CSS for styling
        layout.getStyleClass().add("root");
        
        Label welcomeLabel = new Label("Choose data file\n\n\n");
        welcomeLabel.setFont(Font.font("Century Gothic", FontWeight.BOLD, 30));
        welcomeLabel.setStyle("-fx-text-fill: #000000;");
        
        ImageView background = new ImageView(new Image("file:4.jpg"));
        background.fitWidthProperty().bind(layout.widthProperty());
        background.fitHeightProperty().bind(layout.heightProperty());

        
        String[] strings = {
        		"Choose cities file",
        		"Choose dimensions file",
                "Choose Map"
        };
           
        Button[] buttons = new Button [strings.length];   
        setupButtons(strings, buttons, primaryStage);
            
        VBox ArrangementButtons = new VBox(60,welcomeLabel);
        ArrangementButtons.setPadding(new Insets(30));
        ArrangementButtons.setAlignment(Pos.TOP_CENTER);
        ArrangementButtons.getChildren().addAll(buttons);
        ArrangementButtons.getChildren().add(createControlSceneHBox());

        
        layout.getChildren().addAll(background,ArrangementButtons);
    }
	
	
    public void setupButtons(String[] strings, Button[] buttons, Stage primaryStage) {
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
        	FileChooser fileChooser = new FileChooser();
            fileChooser.setInitialDirectory(new File(System.getProperty("user.dir"))); // Set the initial directory to the user's current directory
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Excel file", "*.xlsx")); // Set the file extension filter
            File selectedFile = fileChooser.showOpenDialog(primaryStage); // Show the file chooser and get the selected file


            if (selectedFile != null && !selectedFile.getName().endsWith(".xlsx")) {
                SceneManager.showAlert("Error", ""); 
            } else if (selectedFile != null) {
            	citiesFile = selectedFile.getName();
            	buttons[0].setGraphic(new ImageView(new Image("file:data\\verification.png")));
            	buttons[0].setContentDisplay(ContentDisplay.RIGHT);
            }       
        });
        
        buttons[1].setOnAction(e -> {
        	FileChooser fileChooser = new FileChooser();
            fileChooser.setInitialDirectory(new File(System.getProperty("user.dir"))); // Set the initial directory to the user's current directory
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Excel file", "*.xlsx")); // Set the file extension filter
            File selectedFile = fileChooser.showOpenDialog(primaryStage); // Show the file chooser and get the selected file

            if (selectedFile != null && !selectedFile.getName().endsWith(".xlsx")) {
                SceneManager.showAlert("Error", ""); 
            } else if (selectedFile != null) {
            	mapDimensionsFile = selectedFile.getName();
            	buttons[1].setGraphic(new ImageView(new Image("file:data\\verification.png")));
            	buttons[1].setContentDisplay(ContentDisplay.RIGHT);
            } 
        });
        
        buttons[2].setOnAction(e -> {
        	FileChooser fileChooser = new FileChooser();
        	fileChooser.setInitialDirectory(new File(System.getProperty("user.dir"))); // Set the initial directory to the user's current directory
        	// Set the file extension filters to allow only image files
        	fileChooser.getExtensionFilters().addAll(
        	    new FileChooser.ExtensionFilter("JPG", "*.jpg"),
        	    new FileChooser.ExtensionFilter("PNG", "*.png")
        	);
        	File selectedFile = fileChooser.showOpenDialog(primaryStage); // Show the file chooser and get the selected file


            if (selectedFile != null) {
            	mapPath = selectedFile.getName();
            	buttons[2].setGraphic(new ImageView(new Image("file:data\\verification.png")));
            	buttons[2].setContentDisplay(ContentDisplay.RIGHT);
            } 
        });
    }
    
    private HBox createControlSceneHBox() {
        // Create a submit button with an image and styling.
        Button submitButton = new Button("Submit");
        submitButton.setGraphic(new ImageView(new Image("file:data\\submit.png")));
        submitButton.setContentDisplay(ContentDisplay.RIGHT);
        submitButton.getStyleClass().add("custom-button");
        submitButton.setPrefWidth(150);
        submitButton.setPrefHeight(45);
        submitButton.setOnAction(e -> {
        	if (mapDimensionsFile!=null && mapPath!=null && citiesFile!=null) {
        		Graph graph = SceneManager.readGraph(citiesFile,mapDimensionsFile,mapPath);
        		SceneManager.setScene(new ResultScene(graph));
        	}
        	else {
				SceneManager.showAlert("Error","You must choose the files first");
			}
        });

        // Create a return button with an image and styling.
        Button returnButton = new Button("Back");
        returnButton.setGraphic(new ImageView(new Image("file:data\\reply.png")));
        returnButton.getStyleClass().add("custom-button");
        returnButton.setPrefWidth(150);
        returnButton.setPrefHeight(45);
        returnButton.setOnAction(e -> SceneManager.setMainScene());

        // Create an HBox to hold the buttons.
        HBox controlSceneHBox = new HBox(40);
        controlSceneHBox.setAlignment(Pos.TOP_CENTER);
        controlSceneHBox.getChildren().addAll(returnButton, submitButton);

        return controlSceneHBox;
    }

}
