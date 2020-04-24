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

    @Override
    public String toString() {
        return "Attempt{" +
                "mRemainingSeconds=" + mRemainingSeconds +
                ", mKind=" + mKind +
                '}';
    }
}
