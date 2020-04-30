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
class HomePauseBtnTest {
    private Label timer;
    private Label attemptTitle;
    private Button play;
    private Button pause;
    private Button restart;
    private Button workoutTimeUp;
    private Button breakTimeUp;
    private Home home;
    private VBox container;

    @Start
    private void start(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/home.fxml"));
        Parent root = fxmlLoader.load();
        home = fxmlLoader.getController();
        primaryStage.setScene(new Scene(root, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT));
        timer = (Label) root.lookup("#time");
        attemptTitle = (Label) root.lookup("#title");
        play = (Button) root.lookup("#play");
        pause = (Button) root.lookup("#pause");
        restart = (Button) root.lookup("#restart");
        workoutTimeUp = (Button) root.lookup("#workoutTimeUp");
        breakTimeUp = (Button) root.lookup("#breakTimeUp");
        container = (VBox) root.lookup("#container");
        primaryStage.show();
    }
    
    @Test
    void pauseButtonWorksCorrectly(FxRobot robot) {
        String expectedTimerTextPrep = Timer.createTimerString(Constants.SECONDS_STEP - 2);
        String expectedTimerTextWorkout = Timer.createTimerString(Constants.SECONDS_STEP - 2);
        String expectedTimerTextBreak = Timer.createTimerString(Constants.SECONDS_STEP - 4);
        robot.clickOn(workoutTimeUp);
        robot.clickOn(breakTimeUp);
        robot.clickOn(play);

        WaitForAsyncUtils.sleep(2, TimeUnit.SECONDS);

        robot.clickOn(pause);
        assertThat(timer).hasText(expectedTimerTextPrep);
        assertThat(home.getTimerText()).isEqualTo(expectedTimerTextPrep);
        assertThat(container.getStyleClass().toString()).isEqualTo("root prep");
        robot.clickOn(play);
        assertThat(container.getStyleClass().toString()).isEqualTo("root prep playing");

        WaitForAsyncUtils.sleep(Constants.SECONDS_STEP, TimeUnit.SECONDS);

        robot.clickOn(pause);
        assertThat(timer).hasText(expectedTimerTextWorkout);
        assertThat(home.getTimerText()).isEqualTo(expectedTimerTextWorkout);
        assertThat(container.getStyleClass().toString()).isEqualTo("root workout");
        robot.clickOn(play);
        assertThat(container.getStyleClass().toString()).isEqualTo("root workout playing");

        WaitForAsyncUtils.sleep(Constants.SECONDS_STEP + 2, TimeUnit.SECONDS);

        robot.clickOn(pause);
        assertThat(timer).hasText(expectedTimerTextBreak);
        assertThat(home.getTimerText()).isEqualTo(expectedTimerTextBreak);
        assertThat(container.getStyleClass().toString()).isEqualTo("root break");
        robot.clickOn(play);
        assertThat(container.getStyleClass().toString()).isEqualTo("root break playing");

        robot.clickOn(restart);
        appResetsCorrectly();
    }

    void appResetsCorrectly(){
        assertThat(timer).hasText("00:00");
        assertThat(home.getTimerText()).isEqualTo("00:00");
        assertThat(container.getStyleClass().toString()).isEqualTo("root");
    }
}