package com.radsoltan.controllers;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TimerTest {
    @Test
    void usingZeroAsInputGivesCorrectString() {
        String timer = Timer.createTimerString(0);

        assertEquals("00:00", timer);
    }

    @Test
    void usingTimeLessThanOneMinuteGivesCorrectString() {
        String timer = Timer.createTimerString(44);

        assertEquals("00:44", timer);
    }

    @Test
    void usingTimeWithMinutesAndSecondsGivesCorrectString() {
        String timer = Timer.createTimerString(567);

        assertEquals("09:27",timer);
    }

    @Test
    void usingTimesInMinutesGivesCorrectString() {
        String timer = Timer.createTimerString(180);

        assertEquals("03:00",timer);
    }
}