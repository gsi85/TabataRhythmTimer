package com.sisa.tabata.util;

import android.text.Html;
import android.text.Spanned;

import com.sisa.tabata.R;
import com.sisa.tabata.TabataApplication;

import java.util.concurrent.TimeUnit;

/**
 * Created by Laca on 2015.02.23..
 */
public class TimeFormatter {


    private static final String MINUTES_DOWN_TO_HUNDREDS_SEC_PATTERN = TabataApplication.getAppContext().getString(R.string.minutes_down_to_hundreds_sec_pattern);
    private static final String HOURS_DOWN_TO_SECONDS_PATTERN = "%02d:%02d:%02d";
    private static final String MINUTEST_DOWN_TO_SECOND_PATTERN = "%02d:%02d";
    private static final String TIME_SECTION_DIVIDER = ":";
    private static final int MILLIS_IN_ONE_SECOND = 1000;
    private static final int SECONDS_IN_ONE_MINUTE = 60;

    public static Spanned formatMilliSecondsToMinuteSecondHundredSec(long milliSeconds) {
        long minutes = TimeUnit.MILLISECONDS.toMinutes(milliSeconds);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(milliSeconds) - TimeUnit.MINUTES.toSeconds(minutes);
        long hundredSec = (milliSeconds % MILLIS_IN_ONE_SECOND) / 100;
        return Html.fromHtml(String.format(MINUTES_DOWN_TO_HUNDREDS_SEC_PATTERN, minutes, seconds, hundredSec));
    }

    public static String formatMilliSecondsToHourMinuteSecond(long milliSeconds) {
        long hours = TimeUnit.MILLISECONDS.toHours(milliSeconds);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(milliSeconds) - TimeUnit.HOURS.toMinutes(hours);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(milliSeconds) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(milliSeconds));
        return String.format(HOURS_DOWN_TO_SECONDS_PATTERN, hours, minutes, seconds);
    }

    public static String formatSecondsToHourMinuteSecond(long totalSeconds) {
        long hours = TimeUnit.SECONDS.toHours(totalSeconds);
        long minutes = TimeUnit.SECONDS.toMinutes(totalSeconds) - TimeUnit.HOURS.toMinutes(hours);
        long seconds = totalSeconds - TimeUnit.MINUTES.toSeconds(TimeUnit.SECONDS.toMinutes(totalSeconds));
        return String.format(HOURS_DOWN_TO_SECONDS_PATTERN, hours, minutes, seconds);
    }

    public static String formatSecondsToMinuteSecond(long secondsTotal) {
        long minutes = TimeUnit.SECONDS.toMinutes(secondsTotal);
        long seconds = secondsTotal - TimeUnit.MINUTES.toSeconds(minutes);
        return String.format(MINUTEST_DOWN_TO_SECOND_PATTERN, minutes, seconds);
    }

    public static int getSecondsFromMinuteSecond(String minuteSecond) {
        String[] parts = minuteSecond.split(TIME_SECTION_DIVIDER);
        return Integer.parseInt(parts[0]) * SECONDS_IN_ONE_MINUTE + Integer.parseInt(parts[1]);
    }

}
