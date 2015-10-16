package com.sisa.tabata.ui.listener.workout;

import com.google.inject.Inject;
import com.sisa.tabata.R;
import com.sisa.tabata.dao.loader.WorkoutManager;
import com.sisa.tabata.media.service.MediaPlayerService;
import com.sisa.tabata.ui.domain.SerializedWorkout;
import com.sisa.tabata.ui.progressbar.CurrentRoundProgressBar;
import com.sisa.tabata.ui.progressbar.TotalWorkoutProgressBar;
import com.sisa.tabata.ui.timer.WorkoutCountDownTimerManager;

import android.view.View;
import android.widget.ImageButton;

import roboguice.inject.ContextSingleton;
import roboguice.inject.InjectView;

/**
 * Play button click listener.
 *
 * @author Laszlo Sisa
 */
@ContextSingleton
public class PlayButtonClickListener extends AbstractWorkoutActivityButtonClickListener {

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
    @InjectView(R.id.playButton)
    private ImageButton playButton;

    /**
     * Resets the workout.
     */
    public void resetWorkout() {
        if (workoutCountDownTimerManager.isTimerSet()) {
            pauseTimer();
            workoutCountDownTimerManager.setFinished();
            workoutCountDownTimerManager.unloadWorkoutCountDownTimer();
        }
        checkCreateTimer();
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        checkFinished();
        checkCreateTimer();
        pauseResumeTimer();
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

    private void resumeTimer() {
        mediaPlayerService.play();
        playButton.setImageResource(android.R.drawable.ic_media_pause);
        playButton.setBackgroundResource(R.drawable.bg_pause_button);
        playButton.setKeepScreenOn(true);
        workoutCountDownTimerManager.resume();
    }

    private void pauseTimer() {
        mediaPlayerService.pause();
        playButton.setImageResource(android.R.drawable.ic_media_play);
        playButton.setBackgroundResource(R.drawable.bg_play_button);
        playButton.setKeepScreenOn(false);
        workoutCountDownTimerManager.pause();
    }

}
