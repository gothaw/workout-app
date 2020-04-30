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
import org.testfx.util.WaitForAsyncUtils;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static org.testfx.assertions.api.Assertions.assertThat;

@ExtendWith(ApplicationExtension.class)
class HomeAttemptTest {
    private Label attemptTitle;
    private Label timer;
    private Button play;
    private Button restart;
    private Button workoutTimeUp;
    private Button breakTimeUp;
    private VBox container;

    @Start
    private void start(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/home.fxml"));
        Parent root = fxmlLoader.load();
        primaryStage.setScene(new Scene(root, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT));
        timer = (Label) root.lookup("#time");
        attemptTitle = (Label) root.lookup("#title");
        play = (Button) root.lookup("#play");
        restart = (Button) root.lookup("#restart");
        workoutTimeUp = (Button) root.lookup("#workoutTimeUp");
        breakTimeUp = (Button) root.lookup("#breakTimeUp");
        container = (VBox) root.lookup("#container");
        primaryStage.show();
    }

    @Test
    void preparationAttemptStartsCorrectly(FxRobot robot) {
        String expectedTimerText = Timer.createTimerString(Constants.PREP_DURATION);
        robot.clickOn(workoutTimeUp);
        robot.clickOn(breakTimeUp);
        robot.clickOn(play);
        assertThat(timer).hasText(expectedTimerText);
        assertThat(container.getStyleClass().toString()).isEqualTo("root prep playing");
        assertThat(attemptTitle).hasText(Constants.PREP_DESC);
        robot.clickOn(restart);
        appResetsCorrectly();
    }

    @Test
    void afterPreparationTimeAppGoesIntoWorkoutTimeCorrectly(FxRobot robot) {
        String expectedTimerText = Timer.createTimerString(2 * Constants.SECONDS_STEP);
        robot.clickOn(workoutTimeUp);
        robot.clickOn(workoutTimeUp);
        robot.clickOn(breakTimeUp);
        robot.clickOn(play);
        WaitForAsyncUtils.sleep(Constants.PREP_DURATION, TimeUnit.SECONDS);
        assertThat(timer).hasText(expectedTimerText);
        assertThat(container.getStyleClass().toString()).isEqualTo("root workout playing");
        assertThat(attemptTitle).hasText(Constants.WORKOUT_DESC);
        robot.clickOn(restart);
        appResetsCorrectly();
    }

    @Test
    void afterWorkoutTimeAppGoesIntoBreakTimeCorrectly(FxRobot robot) {
        String expectedTimerText = Timer.createTimerString(2 * Constants.SECONDS_STEP);
        robot.clickOn(workoutTimeUp);
        robot.clickOn(breakTimeUp);
        robot.clickOn(breakTimeUp);
        robot.clickOn(play);
        WaitForAsyncUtils.sleep(Constants.PREP_DURATION + Constants.SECONDS_STEP, TimeUnit.SECONDS);
        assertThat(timer).hasText(expectedTimerText);
        assertThat(container.getStyleClass().toString()).isEqualTo("root break playing");
        assertThat(attemptTitle).hasText(Constants.BREAK_DESC);
        robot.clickOn(restart);
        appResetsCorrectly();
    }

    @Test
    void afterBreakTimeAppGoesIntoWorkoutTimeCorrectly(FxRobot robot) {
        String expectedTimerText = Timer.createTimerString(Constants.SECONDS_STEP);
        robot.clickOn(workoutTimeUp);
        robot.clickOn(breakTimeUp);
        robot.clickOn(play);
        WaitForAsyncUtils.sleep(Constants.PREP_DURATION + 2 * Constants.SECONDS_STEP, TimeUnit.SECONDS);
        assertThat(timer).hasText(expectedTimerText);
        assertThat(container.getStyleClass().toString()).isEqualTo("root workout playing");
        assertThat(attemptTitle).hasText(Constants.WORKOUT_DESC);
        robot.clickOn(restart);
        appResetsCorrectly();
    }

    void appResetsCorrectly(){
        assertThat(timer).hasText("00:00");
        assertThat(container.getStyleClass().toString()).isEqualTo("root");
        assertThat(attemptTitle).hasText(Constants.DEFAULT_DESC);
    }
}