package com.radsoltan.controllers;

import com.radsoltan.util.Constants;
import com.radsoltan.util.Timer;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.io.IOException;

import static org.testfx.assertions.api.Assertions.assertThat;

@ExtendWith(ApplicationExtension.class)
class HomePrepAttemptTest {
    private Label timer;
    private Label workoutConfig;
    private Label breakConfig;
    private Label attemptTitle;
    private Button play;
    private Button pause;
    private Button restart;
    private Button workoutTimeUp;
    private Button workoutTimeDown;
    private Button breakTimeUp;
    private Button breakTimeDown;
    private Home home;
    private VBox container;

    @Start
    private void start(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/home.fxml"));
        Parent root = fxmlLoader.load();
        home = fxmlLoader.getController();
        primaryStage.setScene(new Scene(root, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT));
        timer = (Label) root.lookup("#time");
        workoutConfig = (Label) root.lookup("#workoutTimeConfig");
        breakConfig = (Label) root.lookup("#breakTimeConfig");
        attemptTitle = (Label) root.lookup("#title");
        play = (Button) root.lookup("#play");
        pause = (Button) root.lookup("#pause");
        restart = (Button) root.lookup("#restart");
        workoutTimeUp = (Button) root.lookup("#workoutTimeUp");
        workoutTimeDown = (Button) root.lookup("#workoutTimeDown");
        breakTimeUp = (Button) root.lookup("#breakTimeUp");
        breakTimeDown = (Button) root.lookup("#breakTimeDown");
        container = (VBox) root.lookup("#container");
        primaryStage.show();
    }
}