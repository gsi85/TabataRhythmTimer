package com.sisa.tabata.ui.listener.workout;

import android.view.View;
import android.widget.ImageButton;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.sisa.tabata.R;
import com.sisa.tabata.TabataApplication;
import com.sisa.tabata.ui.domain.SerializedWorkout;
import com.sisa.tabata.ui.progressbar.CurrentRoundProgressBar;
import com.sisa.tabata.ui.progressbar.TotalWorkoutProgressBar;
import com.sisa.tabata.ui.timer.CountDownTimerWithPause;
import com.sisa.tabata.ui.timer.WorkoutCountDownTimer;
import com.sisa.tabata.dao.loader.LoadedWorkoutProvider;

import roboguice.RoboGuice;

/**
 * Created by Laca on 2015.02.22..
 */
@Singleton
public class PlayButtonClickListener implements View.OnClickListener {

    @Inject
    private CurrentRoundProgressBar currentRoundProgressBar;
    @Inject
    private TotalWorkoutProgressBar totalWorkoutProgressBar;
    @Inject
    private LoadedWorkoutProvider loadedWorkoutProvider;

    private CountDownTimerWithPause workoutCountDownTimer;
    private ImageButton playButton;

    public PlayButtonClickListener() {
        RoboGuice.injectMembers(TabataApplication.getAppContext(), this);
    }

    public void resetWorkout() {
        if (workoutCountDownTimer != null) {
            pauseTimer();
            workoutCountDownTimer.setFinished(true);
            workoutCountDownTimer = null;
        }
        checkCreateTimer();
    }

    @Override
    public void onClick(View v) {
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
            workoutCountDownTimer = new WorkoutCountDownTimer(serializedWorkout, currentRoundProgressBar, totalWorkoutProgressBar, playButton).create();
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
        workoutCountDownTimer.resume();
    }

    private void pauseTimer() {
        playButton.setImageResource(android.R.drawable.ic_media_play);
        playButton.setBackgroundResource(R.drawable.bg_play_button);
        workoutCountDownTimer.pause();
    }

    public void setPlayButton(ImageButton playButton) {
        this.playButton = playButton;
    }
}
