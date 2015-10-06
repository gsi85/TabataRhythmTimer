package com.sisa.tabata.ui.progressbar;

import com.google.inject.Inject;
import com.sisa.tabata.R;
import com.sisa.tabata.media.service.EffectPlayerService;
import com.sisa.tabata.media.service.MediaPlayerService;
import com.sisa.tabata.ui.domain.SerializedWorkoutSection;
import com.sisa.tabata.ui.domain.WorkoutType;
import com.sisa.tabata.ui.drawable.CircularProgressBarDrawable;
import com.sisa.tabata.util.TimeFormatter;

import android.widget.TextView;

import roboguice.inject.ContextSingleton;
import roboguice.inject.InjectView;

/**
 * Progress bar representing the progress of the current workout round.
 *
 * @author Laszlo sisa
 */
@ContextSingleton
public class CurrentRoundProgressBar {

    private static final String SECTION_TEXT_PATTERN = "SECTION %d of %d";
    private static final String ROUND_TEXT_PATTERN = "ROUND %d of %d";
    private static final int DEFAULT_FIRST_BEEP_IN_MILLIS = 3000;
    private static final int BEEP_INTERVAL_MILLIS = 1000;
    private static final int LAST_BEEP_IN_MILLIS = 40;

    @InjectView(R.id.currentBlockCounter)
    private TextView currentBlockCounter;
    @InjectView(R.id.roundCounter)
    private TextView roundCounter;
    @InjectView(R.id.sectionCounter)
    private TextView sectionCounter;
    @InjectView(R.id.workoutTypeText)
    private TextView workoutTypeText;

    @Inject
    private CircularProgressBarDrawable circularProgressBar;
    @Inject
    private EffectPlayerService effectPlayerService;
    @Inject
    private MediaPlayerService mediaPlayerService;
    private int nextBeepNotification;

    /**
     * Initializes the progress bar.
     *
     * @param maxMilliSeconds total length of round in milli seconds
     * @param numberOfTotalSections total section in workout
     * @param serializedWorkoutSection {@link SerializedWorkoutSection}
     */
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

    /**
     * Updates the progress bar.
     *
     * @param millisUntilFinished milli seconds remaining in round
     */
    public void update(long millisUntilFinished) {
        circularProgressBar.update(millisUntilFinished);
        currentBlockCounter.setText(TimeFormatter.formatMilliSecondsToMinuteSecondHundredSec(millisUntilFinished));
        checkPlayBeep(millisUntilFinished);
    }

    /**
     * Sets the progress bar in finished state.
     *
     * @param workoutOver flag indicating if the current round was the last in the workout
     */
    public void setFinishedState(boolean workoutOver) {
        update(0);
        playRoundFinishEffect(workoutOver);
        workoutTypeText.setText(WorkoutType.FINISHED.getDisplayText());
        circularProgressBar.setBackgroundPaintColor(WorkoutType.FINISHED.getBackGroundColor());
    }

    private void checkPlayBeep(long millisUntilFinished) {
        if (millisUntilFinished <= nextBeepNotification) {
            mediaPlayerService.quieten();
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
            mediaPlayerService.pause();
        } else {
            effectPlayerService.playLongBeep();
            mediaPlayerService.louden();
        }
    }

}
