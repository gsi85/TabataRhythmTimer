package com.sisa.tabata.ui.timer;

import static com.sisa.tabata.validation.Validation.notEmpty;

import java.util.concurrent.atomic.AtomicBoolean;

import com.google.inject.Singleton;
import com.sisa.tabata.ui.domain.SerializedWorkout;
import com.sisa.tabata.ui.progressbar.CurrentRoundProgressBar;
import com.sisa.tabata.ui.progressbar.TotalWorkoutProgressBar;

import android.widget.ImageButton;

/**
 * Manager for {@link WorkoutCountDownTimer}.
 *
 * @author Laszlo Sisa
 */
@Singleton
public class WorkoutCountDownTimerManager {

    private AtomicBoolean workoutInProgress = new AtomicBoolean(false);
    private CountDownTimerWithPause workoutCountDownTimer;

    /**
     * Creates a new {@link CountDownTimerWithPause}.
     *
     * @param serializedWorkout {@link SerializedWorkout}
     * @param currentRoundProgressBar {@link CurrentRoundProgressBar}
     * @param totalWorkoutProgressBar {@link TotalWorkoutProgressBar}
     * @param playButton {@link ImageButton}
     */
    public void createWorkoutCountDownTimer(final SerializedWorkout serializedWorkout, final CurrentRoundProgressBar currentRoundProgressBar,
            final TotalWorkoutProgressBar totalWorkoutProgressBar, final ImageButton playButton) {
        workoutCountDownTimer = new WorkoutCountDownTimer(serializedWorkout, currentRoundProgressBar, totalWorkoutProgressBar, playButton).create();
        workoutInProgress.set(false);
    }

    /**
     * Sets {@code workoutCountDownTimer} to null.
     */
    public void unloadWorkoutCountDownTimer() {
        workoutCountDownTimer = null;
    }

    /**
     * Pauses the counter.
     */
    public void pause() {
        if (notEmpty(workoutCountDownTimer)) {
            workoutCountDownTimer.pause();
        }
    }

    /**
     * Resumes the counter.
     */
    public void resume() {
        if (notEmpty(workoutCountDownTimer)) {
            workoutInProgress.set(true);
            workoutCountDownTimer.resume();
        }
    }

    /**
     * Sets timer to finished state.
     */
    public void setFinished() {
        workoutInProgress.set(false);
        workoutCountDownTimer.setFinished(true);
    }

    /**
     * Returns the current section count.
     *
     * @return section count
     */
    public int getSectionCount() {
        return workoutCountDownTimer.getSectionCounter();
    }

    public boolean isPaused() {
        return workoutCountDownTimer.isPaused();
    }

    public boolean isWorkoutInProgress() {
        return workoutInProgress.get();
    }

    public boolean isTimerSet() {
        return notEmpty(workoutCountDownTimer);
    }

    public boolean isFinished() {
        return workoutCountDownTimer.isFinished();
    }
}
