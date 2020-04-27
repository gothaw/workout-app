package com.radsoltan.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AttemptTest {
    private Attempt attempt;

    @BeforeEach
    void setUp() {
        AttemptKind attemptKind = new AttemptKind("break", "take a break please", 100);
        attempt = new Attempt(attemptKind);
    }

    @Test
    void reducingSecondsForValidAttempt() {
        attempt.tick();
        assertEquals(99, attempt.getRemainingSeconds());
    }
}