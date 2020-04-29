package com.radsoltan.controllers;

import com.radsoltan.util.Constants;
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
import org.testfx.assertions.api.Assertions;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ApplicationExtension.class)
class HomeLaunchTest {
    private Label timer;
    private Label workoutConfig;
    private Label breakConfig;
    private Label attemptTitle;
    private Button play;
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
        container = (VBox) root.lookup("#container");
        primaryStage.show();
    }

    @Test
    void mainTimerHasCorrectTimeWhenAppLaunches() {
        Assertions.assertThat(timer).hasText("00:00");
        Assertions.assertThat(home.getTimerText()).isEqualTo("00:00");
    }

    @Test
    void workoutAndBreakConfigTimersHaveCorrectTimeWhenAppLaunches() {
        Assertions.assertThat(workoutConfig).hasText("00:00");
        Assertions.assertThat(breakConfig).hasText("00:00");
        Assertions.assertThat(home.getWorkoutTimeText()).isEqualTo("00:00");
        Assertions.assertThat(home.getWorkoutTimeText()).isEqualTo("00:00");
    }

    @Test
    void attemptTitleIsCorrectWhenAppLaunches() {
        Assertions.assertThat(attemptTitle).hasText(Constants.DEFAULT_DESC);
        Assertions.assertThat(home.getTitle()).isEqualTo(Constants.DEFAULT_DESC);
    }

    @Test
    void containerStylingIsCorrectWhenAppLaunches() {
        Assertions.assertThat(container.getStyleClass().toString()).isEqualTo("root");
    }

    @Test
    void timerDoesNotStartWithoutSettingUpConfig(FxRobot robot) {
        robot.clickOn(play);
        mainTimerHasCorrectTimeWhenAppLaunches();
        workoutAndBreakConfigTimersHaveCorrectTimeWhenAppLaunches();
        attemptTitleIsCorrectWhenAppLaunches();
        containerStylingIsCorrectWhenAppLaunches();
    }
}