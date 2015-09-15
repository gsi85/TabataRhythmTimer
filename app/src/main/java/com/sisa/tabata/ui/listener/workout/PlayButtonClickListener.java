package com.sisa.tabata.ui.listener.workout;

import com.google.inject.Inject;
import com.sisa.tabata.R;
import com.sisa.tabata.dao.loader.LoadedWorkoutProvider;
import com.sisa.tabata.ui.domain.SerializedWorkout;
import com.sisa.tabata.ui.progressbar.CurrentRoundProgressBar;
import com.sisa.tabata.ui.progressbar.TotalWorkoutProgressBar;
import com.sisa.tabata.ui.timer.CountDownTimerWithPause;
import com.sisa.tabata.ui.timer.WorkoutCountDownTimer;

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
public class PlayButtonClickListener implements View.OnClickListener {

    @Inject
    private CurrentRoundProgressBar currentRoundProgressBar;
    @Inject
    private TotalWorkoutProgressBar totalWorkoutProgressBar;
    @Inject
    private LoadedWorkoutProvider loadedWorkoutProvider;
    @InjectView(R.id.playButton)
    private ImageButton playButton;

    private CountDownTimerWithPause workoutCountDownTimer;

    /**
     * Resets the workout.
     */
    public void resetWorkout() {
        if (workoutCountDownTimer != null) {
            pauseTimer();
            workoutCountDownTimer.setFinished(true);
            workoutCountDownTimer = null;
        }
        checkCreateTimer();
    }

    @Override
    public void onClick(View view) {
        checkFinished();
        checkCreateTimer();
        pauseResumeTimer();
    }

    private void checkFinished() {
        if (workoutCountDownTimer != null && workoutCountDownTimer.isFinished()) {
            workoutCountDownTimer = null;
        }
    }

    private void checkCreateTimer() {
        if (workoutCountDownTimer == null) {
            SerializedWorkout serializedWorkout = loadedWorkoutProvider.getLoadedSerializedWorkout();
            workoutCountDownTimer = new WorkoutCountDownTimer(serializedWorkout, currentRoundProgressBar, totalWorkoutProgressBar, playButton)
                    .create();
        }
    }

    private void pauseResumeTimer() {
        if (workoutCountDownTimer.isPaused()) {
            resumeTimer();
        } else {
            pauseTimer();
        }
    }

    private void resumeTimer() {
        playButton.setImageResource(android.R.drawable.ic_media_pause);
        playButton.setBackgroundResource(R.drawable.bg_pause_button);
        playButton.setKeepScreenOn(true);
        workoutCountDownTimer.resume();
    }

    private void pauseTimer() {
        playButton.setImageResource(android.R.drawable.ic_media_play);
        playButton.setBackgroundResource(R.drawable.bg_play_button);
        playButton.setKeepScreenOn(false);
        workoutCountDownTimer.pause();
    }

}
