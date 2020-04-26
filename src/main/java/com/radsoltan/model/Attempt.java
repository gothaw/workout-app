package com.radsoltan.model;

/**
 * Instance of the Attempt. It represents actual workout or break attempt with seconds being decreased over time.
 * It uses instance of Attempt Kind to get information about total seconds in the attempt.
 */
public class Attempt {
    private int mRemainingSeconds;
    private final AttemptKind mKind;

    /**
     * Attempt Constructor.
     * @param kind AttemptKind object
     */
    public Attempt(AttemptKind kind) {
        mKind = kind;
        mRemainingSeconds = mKind.getTotalSeconds();
    }

    /**
     * Gets mRemainingSeconds.
     * @return mRemainingSeconds
     */
    public int getRemainingSeconds() {
        return mRemainingSeconds;
    }

    /**
     * Gets mKind.
     * @return mKind
     */
    public AttemptKind getKind() {
        return mKind;
    }

    /**
     * Reduces remaining seconds by one.
     */
    public void tick() {
        mRemainingSeconds--;
    }

    /**
     * Method override for toString()
     * @return String with object description
     */
    @Override
    public String toString() {
        return "Attempt{" +
                "mRemainingSeconds=" + mRemainingSeconds +
                ", mKind=" + mKind +
                '}';
    }
}
