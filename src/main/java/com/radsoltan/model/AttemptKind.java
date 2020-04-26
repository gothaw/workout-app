package com.radsoltan.model;

/**
 * Instances of AttemptKind represent detailed description and configuration for Attempt object.
 * It includes attempt name, description to be displayed on screen and total number of seconds for the attempt.
 */
public class AttemptKind {
    private String mName;
    private String mDescription;
    private int mTotalSeconds;

    /**
     * Attempt Kind Constructor.
     * @param name name of the attempt
     * @param description attempt description to be displayed on stage
     */
    public AttemptKind(String name, String description) {
        mName = name;
        mDescription = description;
        mTotalSeconds = 0;
    }

    /**
     * Attempt Kind Constructor that specifies number of total seconds in the attempt.
     * @param name name of the attempt
     * @param description attempt description to be displayed on stage
     * @param totalSeconds total seconds in the attempt
     */
    public AttemptKind(String name, String description, int totalSeconds) {
        mName = name;
        mDescription = description;
        mTotalSeconds = totalSeconds;
    }

    /**
     * Gets mName.
     * @return mName
     */
    public String getName() {
        return mName;
    }

    /**
     * Sets mName.
     * @param name name of the attempt
     */
    public void setName(String name) {
        mName = name;
    }

    /**
     * Gets mDescription.
     * @return mDescription
     */
    public String getDescription() {
        return mDescription;
    }

    /**
     * Sets mDescription.
     * @param description attempt description to be displayed on stage
     */
    public void setDescription(String description) {
        mDescription = description;
    }

    /**
     * Gets mTotalSeconds.
     * @return mTotalSeconds
     */
    public int getTotalSeconds() {
        return mTotalSeconds;
    }

    /**
     * Sets mTotalSeconds.
     * @param totalSeconds total seconds in the attempt
     */
    public void setTotalSeconds(int totalSeconds) {
        mTotalSeconds = totalSeconds;
    }

    /**
     * Decreases total seconds of the attempt by 15s
     */
    public void decreaseSeconds() {
        if (mTotalSeconds > 0) {
            mTotalSeconds = mTotalSeconds - 15;
        }
    }

    /**
     * Increases total seconds of the attempt by 15s
     */
    public void increaseSeconds() {
        if (mTotalSeconds < 60 * 60) {
            mTotalSeconds = mTotalSeconds + 15;
        }
    }
}
