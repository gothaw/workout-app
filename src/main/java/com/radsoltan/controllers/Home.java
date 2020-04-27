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
import java.util.ArrayList;
import javafx.util.Duration;
import java.util.List;

/**
 * Controller for the main stage. Handles button events and instantiates models.
 */
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
    private final StringProperty mBreakTimeText;
    private final StringProperty mWorkoutTimeText;
    private Timeline mTimeline;
    private final AudioClip mSound;
    private final List<String> mStyles;

    /**
     * Home Constructor.
     * It initializes StringProperty objects for timer and break and workout config. It sets these to 00:00.
     * It initializes new AttemptKinds for break, workout and preparation time and an array list with css classes for state specific styling.
     * The App uses three attempt kinds:
     * 1. Prep
     * 2. Workout
     * 3. Break
     * It loads and audio clip from resources and assigns it to AudioClip field.
     */
    public Home() {
        mTimerText = new SimpleStringProperty();
        mBreakTimeText = new SimpleStringProperty();
        mWorkoutTimeText = new SimpleStringProperty();

        setTimerText(0);
        setBreakTimeText(0);
        setWorkoutTimeText(0);

        mBreak = new AttemptKind(Constants.BREAK_NAME, Constants.BREAK_DESC);
        mWorkout = new AttemptKind(Constants.WORKOUT_NAME, Constants.WORKOUT_DESC);
        mPrep = new AttemptKind(Constants.PREP_NAME, Constants.PREP_DESC, Constants.PREP_DURATION);

        mStyles = new ArrayList<>(List.of(Constants.PREP_NAME, Constants.WORKOUT_NAME, Constants.BREAK_NAME));

        mSound = new AudioClip(getClass().getResource("/sounds/alarm.wav").toExternalForm());
    }

    /**
     * Gets mTimerText String.
     * @return String of mTimerText.
     */
    public String getTimerText() {
        return mTimerText.get();
    }

    /**
     * Gets mTimerText StringProperty.
     * @return mTimerText
     */
    public StringProperty timerTextProperty() {
        return mTimerText;
    }

    /**
     * Sets timerText.
     * @param timerText timer text as a String
     */
    public void setTimerText(String timerText) {
        mTimerText.set(timerText);
    }

    /**
     * Sets timerText. Method overload.
     * @param remainingSeconds remaining seconds as int
     */
    public void setTimerText(int remainingSeconds) {
        setTimerText(Timer.createTimerString(remainingSeconds));
    }

    /**
     * Gets mBreakTimeText String.
     * @return String of mBreakTimeText
     */
    public String getBreakTimeText() {
        return mBreakTimeText.get();
    }

    /**
     * Gets mBreakTimeText StringProperty.
     * @return mBreakTimeText
     */
    public StringProperty breakTimeTextProperty() {
        return mBreakTimeText;
    }

    /**
     * Sets mBreakTimeText.
     * @param breakTimeConfigText timer text as a String
     */
    public void setBreakTimeText(String breakTimeConfigText) {
        mBreakTimeText.set(breakTimeConfigText);
    }

    /**
     * Sets mBreakTimeText. Method overload.
     * @param totalSeconds remaining seconds as int
     */
    public void setBreakTimeText(int totalSeconds) {
        setBreakTimeText(Timer.createTimerString(totalSeconds));
    }

    /**
     * Gets mWorkoutTimeText String.
     * @return String of mWorkoutTimeText
     */
    public String getWorkoutTimeText() {
        return mWorkoutTimeText.get();
    }

    /**
     * Gets mWorkoutTimeText StringProperty.
     * @return mWorkoutTimeText
     */
    public StringProperty workoutTimeTextProperty() {
        return mWorkoutTimeText;
    }

    /**
     * Sets mWorkoutTimeText.
     * @param workoutTimeConfigText timer text as a String
     */
    public void setWorkoutTimeText(String workoutTimeConfigText) {
        mWorkoutTimeText.set(workoutTimeConfigText);
    }

    /**
     * Sets mWorkoutTimeText. Method overload.
     * @param totalSeconds remaining seconds as int.
     */
    public void setWorkoutTimeText(int totalSeconds) {
        setWorkoutTimeText(Timer.createTimerString(totalSeconds));
    }

    /**
     * Prepares attempt based on the instance of the AttemptKind. It instantiates new Attempt object.
     * Sets the attempt title, css styling and timer text based on the total seconds in the Attempt.
     * It creates new Timeline with number of cycles based on the total seconds.
     * It sets new KeyFrame animation for the timer by invoking tick() method on Attempt object and updating the timer text.
     * After animation is finished, an event is triggered, which plays the sound stored in mSound field and recursively prepares another attempt.
     * The app starts with preparation attempt, once it finished it switches between workout and break attempts interchangeably.
     * @param kind instance of AttemptKind with total seconds, name and display description
     */
    private void prepareAttempt(AttemptKind kind) {
        reset();

        // Create new Attempt
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

    /**
     * Reset functionality. It clears css styling, resets the attempt text and timer text to 00:00.
     * Stops the animation and sets current Attempt to null.
     */
    private void reset() {
        clearAttemptStyles();
        setTimerText(0);
        mCurrentAttempt = null;
        title.setText(Constants.DEFAULT_DESC);
        if (mTimeline != null && mTimeline.getStatus() == Animation.Status.RUNNING) {
            mTimeline.stop();
        }
    }

    /**
     * Play timer functionality. It adds 'playing' css class to the container and plays the TimeLine.
     */
    public void playTimer() {
        container.getStyleClass().add("playing");
        mTimeline.play();
    }

    /**
     * Pause timer functionality. It removes 'playing' css class from the container and pauses the TimeLine.
     */
    public void pauseTimer() {
        container.getStyleClass().remove("playing");
        mTimeline.pause();
    }

    /**
     * It adds background styling. Adds css class named after AttemptKind name.
     * @param kind instance of AttemptKind with total seconds, name and display description
     */
    private void addAttemptStyle(AttemptKind kind) {
        container.getStyleClass().add(kind.getName().toLowerCase());
    }

    /**
     * Clears all css styling. Removes `playing` css class from the container and all background styling.
     */
    private void clearAttemptStyles() {
        container.getStyleClass().remove("playing");
        for (String style : mStyles) {
            container.getStyleClass().remove(style);
        }
    }

    /**
     * Handles reset button click event. Invokes reset method.
     * @param actionEvent reset button click
     */
    public void handleRestart(ActionEvent actionEvent) {
        reset();
    }

    /**
     * Handles play button click event. It invokes prepareAttempt method for prep time if no Attempt was initialized.
     * Alternatively, it plays the timer for current Attempt.
     * @param actionEvent play button click
     */
    public void handlePlay(ActionEvent actionEvent) {
        if (mBreak.getTotalSeconds() > 0 && mWorkout.getTotalSeconds() > 0) {
            if (mCurrentAttempt == null) {
                prepareAttempt(mPrep);
            }
            playTimer();
        }
    }

    /**
     * Handles pause button click event. It pauses the timer.
     * @param actionEvent pause button click
     */
    public void handlePause(ActionEvent actionEvent) {
        pauseTimer();
    }

    /**
     * Handles break time increase (arrow up) using configuration spinner.
     * @param actionEvent break time configuration arrow up click
     */
    public void handleBreakTimeIncrease(ActionEvent actionEvent) {
        mBreak.increaseSeconds();
        setBreakTimeText(mBreak.getTotalSeconds());
    }

    /**
     * Handles break time decrease (arrow down) using configuration spinner.
     * @param actionEvent break time configuration arrow down click
     */
    public void handleBreakTimeDecrease(ActionEvent actionEvent) {
        mBreak.decreaseSeconds();
        setBreakTimeText(mBreak.getTotalSeconds());
    }

    /**
     * Handles workout time increase (arrow up) using configuration spinner.
     * @param actionEvent workout time configuration arrow up click
     */
    public void handleWorkoutTimeIncrease(ActionEvent actionEvent) {
        mWorkout.increaseSeconds();
        setWorkoutTimeText(mWorkout.getTotalSeconds());
    }

    /**
     * Handles workout time decrease (arrow down) using configuration spinner.
     * @param actionEvent workout time configuration arrow down click
     */
    public void handleWorkoutTimeDecrease(ActionEvent actionEvent) {
        mWorkout.decreaseSeconds();
        setWorkoutTimeText(mWorkout.getTotalSeconds());
    }
}
