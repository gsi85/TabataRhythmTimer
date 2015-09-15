package com.sisa.tabata.ui.progressbar;

import com.google.inject.Inject;
import com.sisa.tabata.R;
import com.sisa.tabata.util.TimeFormatter;

import android.widget.ProgressBar;
import android.widget.TextView;

import roboguice.inject.ContextSingleton;
import roboguice.inject.InjectView;

/**
 * Progress bar representing the progress of the workout in total.
 *
 * @author Laszlo Sisa
 */
@ContextSingleton
public class TotalWorkoutProgressBar {

    public static final int MAX_PROGRESS_BAR_VALUE = 1000;

    @Inject
    private TimeFormatter timeFormatter;
    @InjectView(R.id.totalRemainingTimeCounter)
    private TextView totalRemainingTimeCounter;
    @InjectView(R.id.workoutProgressBar)
    private ProgressBar workoutProgressBar;

    private long remainingTimeInMillis;
    private long totalTimeInMillis;

    /**
     * Sets up the progress bar.
     *
     * @param totalTimeInMilliSeconds total time in milli seconds
     */
    public void setUp(final long totalTimeInMilliSeconds) {
        totalTimeInMillis = totalTimeInMilliSeconds;
        update(totalTimeInMilliSeconds);
    }

    /**
     * Updates the progress bar.
     *
     * @param millisRemaining time remaining in milli seconds
     */
    public void update(final long millisRemaining) {
        remainingTimeInMillis = millisRemaining;
        updateTotalTimeRemainingText();
        updateWorkOutProgressBar();
    }

    private void updateTotalTimeRemainingText() {
        totalRemainingTimeCounter.setText(TimeFormatter.formatMilliSecondsToHourMinuteSecond(remainingTimeInMillis));
    }

    private void updateWorkOutProgressBar() {
        workoutProgressBar.setProgress(getProgressValue());
    }

    private int getProgressValue() {
        long elapsedTimeInMillis = totalTimeInMillis - remainingTimeInMillis;
        return Math.round((float) elapsedTimeInMillis / totalTimeInMillis * MAX_PROGRESS_BAR_VALUE);
    }

}
