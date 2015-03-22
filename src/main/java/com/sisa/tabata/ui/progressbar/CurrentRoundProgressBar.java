package com.sisa.tabata.ui.progressbar;

import android.widget.TextView;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.sisa.tabata.TabataApplication;
import com.sisa.tabata.media.service.EffectPlayerService;
import com.sisa.tabata.ui.domain.SerializedWorkoutSection;
import com.sisa.tabata.ui.domain.WorkoutType;
import com.sisa.tabata.ui.drawable.CircularProgressBarDrawable;
import com.sisa.tabata.util.TimeFormatter;

import roboguice.RoboGuice;

/**
 * Created by Laca on 2015.02.22..
 */
@Singleton
public class CurrentRoundProgressBar {

    private static final String SECTION_TEXT_PATTERN = "SECTION %d of %d";
    private static final String ROUND_TEXT_PATTERN = "ROUND %d of %d";
    private static final int DEFAULT_FIRST_BEEP_IN_MILLIS = 3000;
    private static final int BEEP_INTERVAL_MILLIS = 1000;
    private static final int LAST_BEEP_IN_MILLIS = 40;

    @Inject
    private CircularProgressBarDrawable circularProgressBar;
    @Inject
    private EffectPlayerService effectPlayerService;
    private TextView currentBlockCounter;
    private TextView roundCounter;
    private TextView sectionCounter;
    private TextView workoutTypeText;
    private int nextBeepNotification;

    public CurrentRoundProgressBar() {
        RoboGuice.injectMembers(TabataApplication.getAppContext(), this);
    }

    public void setUp(long maxMilliSeconds, int numberOfTotalSections, SerializedWorkoutSection serializedWorkoutSection) {
        int currentSection = serializedWorkoutSection.getSectionCount();
        int currentRound = serializedWorkoutSection.getRoundCount();
        int totalRoundsInSection = serializedWorkoutSection.getTotalRoundsInSection();
        circularProgressBar.setMaxValue(maxMilliSeconds);
        circularProgressBar.setBackgroundPaintColor(serializedWorkoutSection.getWorkoutType().getBackGroundColor());
        sectionCounter.setText(String.format(SECTION_TEXT_PATTERN, currentSection, numberOfTotalSections));
        roundCounter.setText(String.format(ROUND_TEXT_PATTERN, currentRound, totalRoundsInSection));
        workoutTypeText.setText(serializedWorkoutSection.getWorkoutType().getDisplayText());
        nextBeepNotification = DEFAULT_FIRST_BEEP_IN_MILLIS;
        update(maxMilliSeconds);
    }

    public void update(long millisUntilFinished) {
        circularProgressBar.update(millisUntilFinished);
        currentBlockCounter.setText(TimeFormatter.formatMilliSecondsToMinuteSecondHundredSec(millisUntilFinished));
        checkPlayBeep(millisUntilFinished);
    }

    public void setFinishedState(boolean workoutOver) {
        update(0);
        playRoundFinishEffect(workoutOver);
        workoutTypeText.setText(WorkoutType.FINISHED.getDisplayText());
        circularProgressBar.setBackgroundPaintColor(WorkoutType.FINISHED.getBackGroundColor());
    }

    private void checkPlayBeep(long millisUntilFinished) {
        if (millisUntilFinished <= nextBeepNotification) {
            effectPlayerService.playShortBeep();
            nextBeepNotification = getNextBeepNotification() > LAST_BEEP_IN_MILLIS ? getNextBeepNotification() : -1;
        }
    }

    private int getNextBeepNotification() {
        return nextBeepNotification - BEEP_INTERVAL_MILLIS;
    }

    private void playRoundFinishEffect(boolean workoutOver) {
        if (workoutOver) {
            effectPlayerService.playWorkoutOver();
        } else {
            effectPlayerService.playLongBeep();
        }
    }

    public void setCurrentBlockCounter(TextView currentBlockCounter) {
        this.currentBlockCounter = currentBlockCounter;
    }

    public void setRoundCounter(TextView roundCounter) {
        this.roundCounter = roundCounter;
    }

    public void setSectionCounter(TextView sectionCounter) {
        this.sectionCounter = sectionCounter;
    }

    public void setWorkoutTypeText(TextView workoutTypeText) {
        this.workoutTypeText = workoutTypeText;
    }
}
