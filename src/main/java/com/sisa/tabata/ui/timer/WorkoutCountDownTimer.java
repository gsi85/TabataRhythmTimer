package com.sisa.tabata.ui.timer;

import android.widget.ImageButton;

import com.sisa.tabata.R;
import com.sisa.tabata.TabataApplication;
import com.sisa.tabata.ui.domain.SerializedWorkout;
import com.sisa.tabata.ui.domain.SerializedWorkoutSection;
import com.sisa.tabata.ui.progressbar.CurrentRoundProgressBar;
import com.sisa.tabata.ui.progressbar.TotalWorkoutProgressBar;

import java.util.concurrent.TimeUnit;

import roboguice.RoboGuice;

/**
 * Created by Laca on 2015.02.28.
 */
public class WorkoutCountDownTimer extends CountDownTimerWithPause {

    public static final int COUNT_DOWN_INTERVAL_MILLIS = 50;

    private final TimeUnit timeUnit;
    private final CurrentRoundProgressBar currentRoundProgressBar;
    private final TotalWorkoutProgressBar totalWorkoutProgressBar;
    private final SerializedWorkout serializedWorkout;
    private final ImageButton playButton;
    private SerializedWorkoutSection currentSection;
    private int sectionCounter;
    private int maxSectionIndex;

    public WorkoutCountDownTimer(SerializedWorkout serializedWorkout, CurrentRoundProgressBar currentRoundProgressBar, TotalWorkoutProgressBar totalWorkoutProgressBar, ImageButton playButton) {
        super(serializedWorkout.getTimeUnit().toMillis(serializedWorkout.getWorkoutDuration()), COUNT_DOWN_INTERVAL_MILLIS, false);
        this.serializedWorkout = serializedWorkout;
        this.currentRoundProgressBar = currentRoundProgressBar;
        this.totalWorkoutProgressBar = totalWorkoutProgressBar;
        this.playButton = playButton;
        timeUnit = serializedWorkout.getTimeUnit();
        maxSectionIndex = serializedWorkout.getWorkoutSections().size() - 1;
        resetProgressBars();
        RoboGuice.injectMembers(TabataApplication.getAppContext(), this);
    }

    @Override
    public void onTick(long millisUntilFinished) {
        updateProgressBars(millisUntilFinished);
    }

    @Override
    public void onFinish() {
        currentRoundProgressBar.setFinishedState(true);
        playButton.setImageResource(android.R.drawable.ic_media_play);
        playButton.setBackgroundResource(R.drawable.bg_play_button);
        setFinished(true);
    }

    @Override
    public void cancel() {
        super.cancel();
        if (isFinished()) {
            resetProgressBars();
        }
    }

    private void resetProgressBars() {
        sectionCounter = 0;
        totalWorkoutProgressBar.setUp(timeUnit.toMillis(serializedWorkout.getWorkoutDuration()), serializedWorkout.getWorkoutName());
        setUpCurrentRoundProgressBar(sectionCounter);
    }

    private void updateProgressBars(long millisUntilFinished) {
        updateCurrentRoundProgressBar(millisUntilFinished);
        totalWorkoutProgressBar.update(millisUntilFinished);
    }

    private void updateCurrentRoundProgressBar(long millisUntilFinished) {
        long millisLeftCurrentSection = getCurrentSectionEndTime(millisUntilFinished);
        if (millisLeftCurrentSection <= 0) {
            if (sectionCounter < maxSectionIndex) {
                currentRoundProgressBar.setFinishedState(false);
                setUpCurrentRoundProgressBar(++sectionCounter);
                millisLeftCurrentSection = getCurrentSectionEndTime(millisUntilFinished);
            }
        }
        currentRoundProgressBar.update(millisLeftCurrentSection);
    }

    private long getCurrentSectionEndTime(long millisUntilFinished) {
        return millisUntilFinished - timeUnit.toMillis(serializedWorkout.getWorkoutDuration() - currentSection.getEndTime());
    }

    private void setUpCurrentRoundProgressBar(int sectionIndex) {
        currentSection = serializedWorkout.getWorkoutSections().get(sectionIndex);
        long maxMilliSeconds = timeUnit.toMillis(currentSection.getEndTime() - currentSection.getStartTime());
        currentRoundProgressBar.setUp(maxMilliSeconds, serializedWorkout.getSectionCount(), currentSection);
    }

}
