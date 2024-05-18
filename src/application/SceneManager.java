package application;

import java.io.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class SceneManager {

    // Reference to the primary stage of the application.
    private static Stage primaryStage;

    // Setter method to set the primary stage.
    public static void setPrimaryStage(Stage stage) {
        primaryStage = stage;
    }

    // Set the main scene using a custom MainScene class.
    public static void setMainScene() {
        primaryStage.setScene(new MainScene(primaryStage));
    }

    // Set a general scene for the primary stage.
    public static void setScene(Scene scene) {
        primaryStage.setScene(scene);
    }

    // Display an alert with specified title and content
    // Because I used it in all the scenes i put it here to avoid repeating it in every scene
    public static void showAlert(String title, String content) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
    
    public static Graph readGraph(String citiesFile,String mapDimensionsFile ,String mapPath) {
    	try {
    		FileInputStream dimensionsInputStream = new FileInputStream(new File(mapDimensionsFile));
            Workbook dimensionsWorkbook = new XSSFWorkbook(dimensionsInputStream);
            Sheet dimensionsFirstSheet = dimensionsWorkbook.getSheetAt(0);
            
            double [] mapDimensions = new double [4];
            for (int i = 0; i < mapDimensions.length; i++) 
                mapDimensions[i] = (double) dimensionsFirstSheet.getRow(i).getCell(1).getNumericCellValue();
    
            dimensionsWorkbook.close();
            dimensionsInputStream.close();
            
            FileInputStream citiesInputStream = new FileInputStream(new File(citiesFile));
            Workbook citiesWorkbook = new XSSFWorkbook(citiesInputStream);
            Sheet citiesFirstSheet = citiesWorkbook.getSheetAt(0);

            int numberOfVertices = (int) citiesFirstSheet.getRow(0).getCell(0).getNumericCellValue();
            int numberOfEdges = (int) citiesFirstSheet.getRow(0).getCell(1).getNumericCellValue();
              
            Graph graph = new Graph(numberOfVertices,mapDimensions,new ImageView(new Image("file:"+mapPath)));

            // Assuming the vertices start from the second row and there are no coordinates provided
            for (int i = 1; i <= numberOfVertices; i++) {
                Row row = citiesFirstSheet.getRow(i);
                String cityName = row.getCell(0).getStringCellValue();
                int state = (int) row.getCell(3).getNumericCellValue();
                if (state==1)
                	graph.addVertex(cityName,(double)row.getCell(1).getNumericCellValue(),(double)row.getCell(2).getNumericCellValue(),true);
                else 
                	graph.addVertex(cityName,(double)row.getCell(1).getNumericCellValue(),(double)row.getCell(2).getNumericCellValue(),false);
            }

            // Assuming the edges start right after the vertices
            for (int i = numberOfVertices + 1; i <= numberOfVertices + numberOfEdges; i++) {
                Row row = citiesFirstSheet.getRow(i);
                String city1 = row.getCell(0).getStringCellValue();
                String city2 = row.getCell(1).getStringCellValue();
                graph.addEdge(city1, city2);
            }

            citiesWorkbook.close();
            citiesInputStream.close();
            
            return graph;
		} catch (Exception e) {
			SceneManager.showAlert("Error",e.getMessage());
		} 
    	return null;
	}

}

