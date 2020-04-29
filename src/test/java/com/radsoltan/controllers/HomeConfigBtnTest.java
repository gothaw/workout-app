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

import static org.testfx.assertions.api.Assertions.*;

@ExtendWith(ApplicationExtension.class)
class HomeConfigBtnTest {
    private Label workoutConfig;
    private Label breakConfig;
    private Button workoutTimeUp;
    private Button workoutTimeDown;
    private Button breakTimeUp;
    private Button breakTimeDown;
    private Home home;

    @Start
    private void start(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/home.fxml"));
        Parent root = fxmlLoader.load();
        home = fxmlLoader.getController();
        primaryStage.setScene(new Scene(root, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT));
        workoutConfig = (Label) root.lookup("#workoutTimeConfig");
        breakConfig = (Label) root.lookup("#breakTimeConfig");
        workoutTimeUp = (Button) root.lookup("#workoutTimeUp");
        workoutTimeDown = (Button) root.lookup("#workoutTimeDown");
        breakTimeUp = (Button) root.lookup("#breakTimeUp");
        breakTimeDown = (Button) root.lookup("#breakTimeDown");
        primaryStage.show();
    }

    @Test
    void breakConfigIsNotAllowedToBeNegative(FxRobot robot) {
        robot.clickOn(breakTimeDown);
        assertThat(breakConfig).hasText("00:00");
        assertThat(home.getBreakTimeText()).isEqualTo("00:00");
    }

    @Test
    void workoutConfigIsNotAllowedToBeNegative(FxRobot robot) {
        robot.clickOn(workoutTimeDown);
        assertThat(workoutConfig).hasText("00:00");
        assertThat(home.getWorkoutTimeText()).isEqualTo("00:00");
    }

    @Test
    void workoutConfigHasCorrectTextWhenControlsAreClicked(FxRobot robot) {
        int timesClickedUp = 5;
        int timesClickedDown = 2;
        String expectedTextAfterUpBtnClicked = getTimerExpectedText(timesClickedUp);
        String expectedTextAfterDownBtnClicked = getTimerExpectedText(timesClickedUp, timesClickedDown);

        upBtnClicked(robot, workoutTimeUp, timesClickedUp);
        assertThat(workoutConfig).hasText(expectedTextAfterUpBtnClicked);
        assertThat(home.getWorkoutTimeText()).isEqualTo(expectedTextAfterUpBtnClicked);

        downBtnClicked(robot, workoutTimeDown, timesClickedDown);
        assertThat(workoutConfig).hasText(expectedTextAfterDownBtnClicked);
        assertThat(home.getWorkoutTimeText()).isEqualTo(expectedTextAfterDownBtnClicked);
    }

    @Test
    void breakConfigHasCorrectTextWhenControlsAreClicked(FxRobot robot) {
        int timesClickedUp = 5;
        int timesClickedDown = 2;
        String expectedTextAfterUpBtnClicked = getTimerExpectedText(timesClickedUp);
        String expectedTextAfterDownBtnClicked = getTimerExpectedText(timesClickedUp, timesClickedDown);

        upBtnClicked(robot, breakTimeUp, timesClickedUp);
        assertThat(breakConfig).hasText(expectedTextAfterUpBtnClicked);
        assertThat(home.getBreakTimeText()).isEqualTo(expectedTextAfterUpBtnClicked);

        downBtnClicked(robot, breakTimeDown, timesClickedDown);
        assertThat(breakConfig).hasText(expectedTextAfterDownBtnClicked);
        assertThat(home.getBreakTimeText()).isEqualTo(expectedTextAfterDownBtnClicked);
    }

    private void upBtnClicked(FxRobot robot, Button upBtn, int timesClickedUp) {
        for (int i = 0; i < timesClickedUp; i++) {
            robot.clickOn(upBtn);
        }
    }

    private void downBtnClicked(FxRobot robot, Button downBtn, int timesClickedDown) {
        for (int i = 0; i < timesClickedDown; i++) {
            robot.clickOn(downBtn);
        }
    }

    private String getTimerExpectedText(int timesUp) {
        return Timer.createTimerString(timesUp * Constants.SECONDS_STEP);
    }

    private String getTimerExpectedText(int timesUp, int timesDown) {
        return Timer.createTimerString((timesUp - timesDown) * Constants.SECONDS_STEP);
    }
}