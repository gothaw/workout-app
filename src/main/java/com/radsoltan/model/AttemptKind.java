package com.radsoltan.model;

public class AttemptKind {

    private String mName;
    private String mDescription;
    private int mTotalSeconds;

    public AttemptKind(String name, String description) {
        mName = name;
        mDescription = description;
        mTotalSeconds = 0;
    }

    public AttemptKind(String name, String description, int totalSeconds) {
        mName = name;
        mDescription = description;
        mTotalSeconds = totalSeconds;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public int getTotalSeconds() {
        return mTotalSeconds;
    }

    public void setTotalSeconds(int totalSeconds) {
        mTotalSeconds = totalSeconds;
    }

    public void decreaseSeconds() {
        if (mTotalSeconds > 0) {
            mTotalSeconds = mTotalSeconds - 15;
        }
    }

    public void increaseSeconds() {
        if (mTotalSeconds < 60 * 60) {
            mTotalSeconds = mTotalSeconds + 15;
        }
    }

    public static String createTimerString(int totalSeconds) {
        int minutes = totalSeconds / 60;
        int seconds = totalSeconds % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }
}
