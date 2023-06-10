package com.karinayyy.lab4.taskone;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class MetroDbFX extends Application {
    /**
     * The start method sets up the scene by loading the FXML file for the main form,
     * creating a scene object and setting it as the primary stage.
     *
     * @param primaryStage the primary stage of the application
     */
    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/karinayyy/lab4/MetroDbForm.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root, 1000, 700);
            primaryStage.setScene(scene);
            Font font = new Font(18);
            String stylesheet = "*, *:hover {\n" +
                    "    -fx-font-size: " + font.getSize() + "px;\n" +
                    "}";
            scene.getStylesheets().add("data:text/css," + stylesheet);
//            Scene.setUserAgentStylesheet(null);
            primaryStage.setTitle("Metro station for DB");
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * The main method is the entry point of the application and calls the launch method to start the application.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
