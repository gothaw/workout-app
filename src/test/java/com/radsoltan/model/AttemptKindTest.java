package com.radsoltan.model;

import com.radsoltan.util.Constants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AttemptKindTest {
    private AttemptKind attemptKind;

    @BeforeEach
    void setUp() {
        attemptKind = new AttemptKind("break", "Take a break");
    }

    @Test
    void decreasingTotalSecondsBelowZeroIsNotAllowed() {
        attemptKind.setTotalSeconds(0);

        attemptKind.decreaseSeconds(Constants.SECONDS_STEP);

        assertEquals(0, attemptKind.getTotalSeconds());
    }

    @Test
    void increasingTotalSecondsAboveSixtyMinutesIsNotAllowed() {
        attemptKind.setTotalSeconds(3600);

        attemptKind.increaseSeconds(Constants.SECONDS_STEP);

        assertEquals(3600, attemptKind.getTotalSeconds());
    }

    @Test
    void decreasingSecondsWithinValidRangeIsAllowed() {
        attemptKind.setTotalSeconds(300);

        attemptKind.decreaseSeconds(Constants.SECONDS_STEP);
        int expectedSeconds = 300 - Constants.SECONDS_STEP;

        assertEquals(expectedSeconds, attemptKind.getTotalSeconds());
    }

    @Test
    void increasingSecondsWithinValidRangeIsAllowed() {
        attemptKind.setTotalSeconds(300);

        attemptKind.increaseSeconds(Constants.SECONDS_STEP);
        int expectedSeconds = 300 + Constants.SECONDS_STEP;

        assertEquals(expectedSeconds, attemptKind.getTotalSeconds());
    }
}