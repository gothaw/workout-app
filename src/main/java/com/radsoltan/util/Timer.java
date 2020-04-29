package com.radsoltan.util;

/**
 * Helper method for displaying minutes and seconds in timer format.
 */
public class Timer {
    /**
     * Static method to get timer string format in mm:ss
     * @param totalSeconds total seconds for timer
     * @return String in format mm:ss
     */
    public static String createTimerString(int totalSeconds) {
        int minutes = totalSeconds / 60;
        int seconds = totalSeconds % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }
}
