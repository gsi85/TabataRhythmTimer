package com.sisa.tabata.ui.listener.workout;

import com.google.inject.Inject;
import com.sisa.tabata.ApplicationContextProvider;
import com.sisa.tabata.R;
import com.sisa.tabata.dao.loader.WorkoutManager;
import com.sisa.tabata.media.service.MediaPlayerService;
import com.sisa.tabata.ui.domain.SerializedWorkout;
import com.sisa.tabata.ui.progressbar.CurrentRoundProgressBar;
import com.sisa.tabata.ui.progressbar.TotalWorkoutProgressBar;
import com.sisa.tabata.ui.timer.NotificationDisplayTimer;
import com.sisa.tabata.ui.timer.WorkoutCountDownTimerManager;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import roboguice.inject.ContextSingleton;
import roboguice.inject.InjectView;

/**
 * Play button click listener.
 *
 * @author Laszlo Sisa
 */
@ContextSingleton
public class PlayButtonClickListener extends AbstractWorkoutActivityButtonClickListener {

    private static final int NOTIFICATION_TIME_IN_MILLIS = 2000;
    private static final String SECTION_TEXT_PATTERN = "SECTION %d of %d";

    @Inject
    private CurrentRoundProgressBar currentRoundProgressBar;
    @Inject
    private TotalWorkoutProgressBar totalWorkoutProgressBar;
    @Inject
    private WorkoutManager workoutManager;
    @Inject
    private MediaPlayerService mediaPlayerService;
    @Inject
    private WorkoutCountDownTimerManager workoutCountDownTimerManager;
    @Inject
    private ApplicationContextProvider applicationContextProvider;
    @InjectView(R.id.workoutNotificationView)
    private TextView workoutNotificationView;
    @InjectView(R.id.playButton)
    private ImageButton playButton;

    @Override
    public void onClick(View view) {
        super.onClick(view);
        if (isWorkoutTimeSet()) {
            checkFinished();
            checkCreateTimer();
            pauseResumeTimer();
        } else {
            String noTimeSet = applicationContextProvider.getStringResource(R.string.main_no_time_set);
            showNotification(noTimeSet);
        }
    }

    /**
     * Resets the workout.
     */
    public void resetWorkout() {
        if (workoutCountDownTimerManager.isTimerSet()) {
            pauseTimer();
            workoutCountDownTimerManager.setFinished();
            workoutCountDownTimerManager.unloadWorkoutCountDownTimer();
        }
        if (isWorkoutTimeSet()) {
            checkCreateTimer();
        }
    }

    /**
     * Resumes the counter.
     */
    public void resumeTimer() {
        mediaPlayerService.play();
        playButton.setImageResource(android.R.drawable.ic_media_pause);
        playButton.setBackgroundResource(R.drawable.bg_pause_button);
        playButton.setKeepScreenOn(true);
        String sectionCounterString = String.format(SECTION_TEXT_PATTERN, workoutCountDownTimerManager.getSectionCount(),
                workoutManager.getLoadedSerializedWorkout().getSectionCount());
        showNotification(sectionCounterString);
        workoutCountDownTimerManager.resume();
    }

    /**
     * Pauses the counter.
     */
    public void pauseTimer() {
        mediaPlayerService.pause();
        playButton.setImageResource(android.R.drawable.ic_media_play);
        playButton.setBackgroundResource(R.drawable.bg_play_button);
        playButton.setKeepScreenOn(false);
        workoutCountDownTimerManager.pause();
    }

    private boolean isWorkoutTimeSet() {
        return workoutManager.getLoadedSerializedWorkout().getWorkoutDuration() > 0;
    }

    private void checkFinished() {
        if (workoutCountDownTimerManager.isTimerSet() && workoutCountDownTimerManager.isFinished()) {
            workoutCountDownTimerManager.unloadWorkoutCountDownTimer();
        }
    }

    private void checkCreateTimer() {
        if (!workoutCountDownTimerManager.isTimerSet()) {
            SerializedWorkout serializedWorkout = workoutManager.getLoadedSerializedWorkout();
            workoutCountDownTimerManager.createWorkoutCountDownTimer(serializedWorkout, currentRoundProgressBar, totalWorkoutProgressBar, playButton);
        }
    }

    private void pauseResumeTimer() {
        if (workoutCountDownTimerManager.isPaused()) {
            resumeTimer();
        } else {
            pauseTimer();
        }
    }

    private void showNotification(final String textToDisplay) {
        new NotificationDisplayTimer(workoutNotificationView, textToDisplay, NOTIFICATION_TIME_IN_MILLIS).start();
    }

}
