package com.radsoltan.controllers;

import com.radsoltan.model.Attempt;
import com.radsoltan.model.AttemptKind;
import com.radsoltan.model.Constants;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class Home {
    @FXML
    private VBox container;
    @FXML
    private Label title;

    private Attempt mCurrentAttempt;
    private final AttemptKind mBreak;
    private final AttemptKind mWorkout;
    private final AttemptKind mPrep;
    private final StringProperty mTimerText;
    private final StringProperty mBreakTimeConfigText;
    private final StringProperty mWorkoutTimeConfigText;
    private Timeline mTimeline;
    private final AudioClip mSound;
    private final List<String> mStyles;

    public Home() {
        mTimerText = new SimpleStringProperty();
        mBreakTimeConfigText = new SimpleStringProperty();
        mWorkoutTimeConfigText = new SimpleStringProperty();

        setTimerText(0);
        setBreakTimeConfigText(0);
        setWorkoutTimeConfigText(0);

        mBreak = new AttemptKind(Constants.BREAK_NAME, Constants.BREAK_DESC);
        mWorkout = new AttemptKind(Constants.WORKOUT_NAME, Constants.WORKOUT_DESC);
        mPrep = new AttemptKind(Constants.PREP_NAME, Constants.PREP_DESC, Constants.PREP_DURATION);

        mStyles = new ArrayList<>(List.of(Constants.PREP_NAME, Constants.WORKOUT_NAME, Constants.BREAK_NAME));

        mSound = new AudioClip(getClass().getResource("/sounds/alarm.wav").toExternalForm());
    }

    /* Getters and Setters */

    public String getTimerText() {
        return mTimerText.get();
    }

    public StringProperty timerTextProperty() {
        return mTimerText;
    }

    public void setTimerText(String timerText) {
        mTimerText.set(timerText);
    }

    public void setTimerText(int remainingSeconds) {
        setTimerText(AttemptKind.createTimerString(remainingSeconds));
    }

    public String getBreakTimeConfigText() {
        return mBreakTimeConfigText.get();
    }

    public StringProperty breakTimeConfigTextProperty() {
        return mBreakTimeConfigText;
    }

    public void setBreakTimeConfigText(String breakTimeConfigText) {
        mBreakTimeConfigText.set(breakTimeConfigText);
    }

    public void setBreakTimeConfigText(int totalSeconds) {
        setBreakTimeConfigText(AttemptKind.createTimerString(totalSeconds));
    }

    public String getWorkoutTimeConfigText() {
        return mWorkoutTimeConfigText.get();
    }

    public StringProperty workoutTimeConfigTextProperty() {
        return mWorkoutTimeConfigText;
    }

    public void setWorkoutTimeConfigText(String workoutTimeConfigText) {
        mWorkoutTimeConfigText.set(workoutTimeConfigText);
    }

    public void setWorkoutTimeConfigText(int totalSeconds) {
        setWorkoutTimeConfigText(AttemptKind.createTimerString(totalSeconds));
    }

    /* Prepare Attempt */

    private void prepareAttempt(AttemptKind kind) {
        reset();
        mCurrentAttempt = new Attempt(kind);
        addAttemptStyle(kind);
        title.setText(kind.getDescription());
        setTimerText(mCurrentAttempt.getRemainingSeconds());

        // Create new Timeline
        mTimeline = new Timeline();
        mTimeline.setCycleCount(kind.getTotalSeconds());
        mTimeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1), e -> {
            mCurrentAttempt.tick();
            setTimerText(mCurrentAttempt.getRemainingSeconds());
        }));

        // Sets an event after timeline is finished
        mTimeline.setOnFinished(e -> {
            mSound.play();
            if (mCurrentAttempt.getKind() == mPrep) {
                prepareAttempt(mWorkout);
            } else {
                prepareAttempt(mCurrentAttempt.getKind() == mWorkout ? mBreak : mWorkout);
            }
            playTimer();
        });
    }

    private void reset() {
        clearAttemptStyles();
        setTimerText(0);
        mCurrentAttempt = null;
        title.setText(Constants.DEFAULT_DESC);
        if (mTimeline != null && mTimeline.getStatus() == Animation.Status.RUNNING) {
            mTimeline.stop();
        }
    }

    public void playTimer() {
        container.getStyleClass().add("playing");
        mTimeline.play();
    }

    public void pauseTimer() {
        container.getStyleClass().remove("playing");
        mTimeline.pause();
    }

    private void addAttemptStyle(AttemptKind kind) {
        container.getStyleClass().add(kind.getName().toLowerCase());
    }

    private void clearAttemptStyles() {
        container.getStyleClass().remove("playing");
        for (String style : mStyles) {
            container.getStyleClass().remove(style);
        }
    }

    /* Button Handlers */

    public void handleRestart(ActionEvent actionEvent) {
        reset();
    }

    public void handlePlay(ActionEvent actionEvent) {
        if (mBreak.getTotalSeconds() > 0 && mWorkout.getTotalSeconds() > 0) {
            if (mCurrentAttempt == null) {
                prepareAttempt(mPrep);
            }
            playTimer();
        }
    }

    public void handlePause(ActionEvent actionEvent) {
        pauseTimer();
    }

    public void handleBreakTimeIncrease(ActionEvent actionEvent) {
        mBreak.increaseSeconds();
        setBreakTimeConfigText(mBreak.getTotalSeconds());
    }

    public void handleBreakTimeDecrease(ActionEvent actionEvent) {
        mBreak.decreaseSeconds();
        setBreakTimeConfigText(mBreak.getTotalSeconds());
    }

    public void handleWorkoutTimeIncrease(ActionEvent actionEvent) {
        mWorkout.increaseSeconds();
        setWorkoutTimeConfigText(mWorkout.getTotalSeconds());
    }

    public void handleWorkoutTimeDecrease(ActionEvent actionEvent) {
        mWorkout.decreaseSeconds();
        setWorkoutTimeConfigText(mWorkout.getTotalSeconds());
    }
}
