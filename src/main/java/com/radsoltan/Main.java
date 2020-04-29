package com.radsoltan;

import com.radsoltan.util.Constants;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * Main class that extends Application
 */
public class Main extends Application {

    /**
     * Start method override. Loads the font from resource folder.
     * Instantiates root node and loads FXML file. It sets properties for the primaryStage.
     * @param primaryStage application stage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        Font.loadFont(getClass().getResource("/fonts/Roboto-Regular.ttf").toExternalForm(), 10);
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/home.fxml"));
        primaryStage.setScene(new Scene(root, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT));
        primaryStage.setResizable(false);
        primaryStage.setTitle(Constants.APP_TITLE);
        primaryStage.getIcons().add(new Image(getClass().getResource("/images/cat.png").toExternalForm()));
        primaryStage.show();
    }

    /**
     * Main Method invokes launch function from Application.java.
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }
}
