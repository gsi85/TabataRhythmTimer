package com.sisa.tabata.ui.progressbar;

import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.sisa.tabata.R;
import com.sisa.tabata.TabataApplication;
import com.sisa.tabata.util.TimeFormatter;

import roboguice.RoboGuice;
import roboguice.inject.InjectView;

/**
 * Created by Laca on 2015.02.23..
 */
@Singleton
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

    public void update(long millisRemaining) {
        remainingTimeInMillis = millisRemaining;
        updateTotalTimeRemainingText();
        updateWorkOutProgressBar();
    }

    public void setUp(long totalTimeInMilliSeconds) {
        totalTimeInMillis = totalTimeInMilliSeconds;
        update(totalTimeInMilliSeconds);
    }

    private void updateTotalTimeRemainingText() {
        totalRemainingTimeCounter.setText(TimeFormatter.formatMilliSecondsToHourMinuteSecond(remainingTimeInMillis));
    }

    private void updateWorkOutProgressBar() {
        workoutProgressBar.setProgress(getProgressValue());
    }

    private int getProgressValue() {
        long elapsedTimeInMillis = totalTimeInMillis - remainingTimeInMillis;
        return Math.round((float) (elapsedTimeInMillis) / totalTimeInMillis * MAX_PROGRESS_BAR_VALUE);
    }

    public void setWorkoutProgressBar(ProgressBar workoutProgressBar) {
        this.workoutProgressBar = workoutProgressBar;
    }

}
