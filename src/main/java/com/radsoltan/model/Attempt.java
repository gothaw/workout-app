package com.radsoltan.model;

public class Attempt {
    private int mRemainingSeconds;
    private final AttemptKind mKind;

    public Attempt(AttemptKind kind) {
        mKind = kind;
        mRemainingSeconds = mKind.getTotalSeconds();
    }

    public int getRemainingSeconds() {
        return mRemainingSeconds;
    }

    public AttemptKind getKind() {
        return mKind;
    }

    public void tick() {
        mRemainingSeconds--;
    }

    /**
     *
     * @param totalSeconds
     * @return
     */
    public static String createTimerString(int totalSeconds) {
        int minutes = totalSeconds / 60;
        int seconds = totalSeconds % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

    @Override
    public String toString() {
        return "Attempt{" +
                "mRemainingSeconds=" + mRemainingSeconds +
                ", mKind=" + mKind +
                '}';
    }
}
