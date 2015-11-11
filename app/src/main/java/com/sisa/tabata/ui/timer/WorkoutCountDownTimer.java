package com.sisa.tabata.ui.timer;

import java.util.concurrent.TimeUnit;

import com.sisa.tabata.R;
import com.sisa.tabata.ui.domain.SerializedWorkout;
import com.sisa.tabata.ui.domain.SerializedWorkoutSection;
import com.sisa.tabata.ui.progressbar.CurrentRoundProgressBar;
import com.sisa.tabata.ui.progressbar.TotalWorkoutProgressBar;
import com.sisa.tabata.ui.timer.domain.CountDownTimerInitializingContext;

import android.widget.ImageButton;

/**
 * Workout count down timer.
 *
 * @author Laszlo Sisa
 */
public class WorkoutCountDownTimer extends CountDownTimerWithPause {

    private final TimeUnit timeUnit;
    private final CurrentRoundProgressBar currentRoundProgressBar;
    private final TotalWorkoutProgressBar totalWorkoutProgressBar;
    private final SerializedWorkout serializedWorkout;
    private final ImageButton playButton;
    private SerializedWorkoutSection currentSection;
    private int sectionCounter;
    private int maxSectionIndex;

    /**
     * DI constructor.
     *
     * @param initContext {@link CountDownTimerInitializingContext}
     */
    public WorkoutCountDownTimer(final CountDownTimerInitializingContext initContext) {
        super(initContext.getSerializedWorkout().getTimeUnit().toMillis(initContext.getSerializedWorkout().getWorkoutDuration()),
                initContext.getRefreshRateInMillis(), false);
        this.serializedWorkout = initContext.getSerializedWorkout();
        this.currentRoundProgressBar = initContext.getCurrentRoundProgressBar();
        this.totalWorkoutProgressBar = initContext.getTotalWorkoutProgressBar();
        this.playButton = initContext.getPlayButton();
        timeUnit = serializedWorkout.getTimeUnit();
        maxSectionIndex = Math.max(0, serializedWorkout.getWorkoutSections().size() - 1);
        resetProgressBars();
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
        playButton.setKeepScreenOn(false);
        totalWorkoutProgressBar.update(0);
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
        totalWorkoutProgressBar.setUp(timeUnit.toMillis(serializedWorkout.getWorkoutDuration()));
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

    @Override
    public int getSectionCounter() {
        return sectionCounter + 1;
    }
}
