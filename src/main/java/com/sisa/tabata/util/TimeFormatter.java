package com.sisa.tabata.util;

import java.util.concurrent.TimeUnit;

import android.text.Html;
import android.text.Spanned;

/**
 * Time formatter.
 *
 * @author Laszlo Sisa
 */
public class TimeFormatter {

    private static final String MINUTES_DOWN_TO_HUNDREDS_SEC_PATTERN = "<big>%02d:%02d</big><small>.%01d</small>";
    private static final String HOURS_DOWN_TO_SECONDS_PATTERN = "%02d:%02d:%02d";
    private static final String MINUTEST_DOWN_TO_SECOND_PATTERN = "%02d:%02d";
    private static final String TIME_SECTION_DIVIDER = ":";
    private static final int MILLIS_IN_ONE_SECOND = 1000;
    private static final int SECONDS_IN_ONE_MINUTE = 60;

    /**
     * Format milli seconds to the following format: mm:ss.hh
     *
     * @param milliSeconds milli seconds to format
     * @return formatted text.
     */
    public static Spanned formatMilliSecondsToMinuteSecondHundredSec(long milliSeconds) {
        long minutes = TimeUnit.MILLISECONDS.toMinutes(milliSeconds);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(milliSeconds) - TimeUnit.MINUTES.toSeconds(minutes);
        long hundredSec = (milliSeconds % MILLIS_IN_ONE_SECOND) / 100;
        return Html.fromHtml(String.format(MINUTES_DOWN_TO_HUNDREDS_SEC_PATTERN, minutes, seconds, hundredSec));
    }

    /**
     * Format milli seconds to the following format: hh:mm:ss
     *
     * @param milliSeconds milli seconds to format
     * @return formatted text.
     */
    public static String formatMilliSecondsToHourMinuteSecond(long milliSeconds) {
        long hours = TimeUnit.MILLISECONDS.toHours(milliSeconds);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(milliSeconds) - TimeUnit.HOURS.toMinutes(hours);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(milliSeconds) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(milliSeconds));
        return String.format(HOURS_DOWN_TO_SECONDS_PATTERN, hours, minutes, seconds);
    }

    /**
     * Format seconds to the following format: hh:mm:ss
     *
     * @param totalSeconds seconds to format
     * @return formatted text.
     */
    public static String formatSecondsToHourMinuteSecond(long totalSeconds) {
        long hours = TimeUnit.SECONDS.toHours(totalSeconds);
        long minutes = TimeUnit.SECONDS.toMinutes(totalSeconds) - TimeUnit.HOURS.toMinutes(hours);
        long seconds = totalSeconds - TimeUnit.MINUTES.toSeconds(TimeUnit.SECONDS.toMinutes(totalSeconds));
        return String.format(HOURS_DOWN_TO_SECONDS_PATTERN, hours, minutes, seconds);
    }

    /**
     * Format seconds to the following format: mm:ss
     *
     * @param secondsTotal seconds to format
     * @return formatted text.
     */
    public static String formatSecondsToMinuteSecond(long secondsTotal) {
        long minutes = TimeUnit.SECONDS.toMinutes(secondsTotal);
        long seconds = secondsTotal - TimeUnit.MINUTES.toSeconds(minutes);
        return String.format(MINUTEST_DOWN_TO_SECOND_PATTERN, minutes, seconds);
    }

    /**
     * Converts string in format of mm:ss to seconds represented as a numeric value
     *
     * @param minuteSecond formatted text to convert
     * @return converted numeric value
     */
    public static int getSecondsFromMinuteSecond(String minuteSecond) {
        String[] parts = minuteSecond.split(TIME_SECTION_DIVIDER);
        return Integer.parseInt(parts[0]) * SECONDS_IN_ONE_MINUTE + Integer.parseInt(parts[1]);
    }

}
