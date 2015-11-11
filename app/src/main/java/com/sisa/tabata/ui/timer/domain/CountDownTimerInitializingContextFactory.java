package com.sisa.tabata.ui.timer.domain;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.sisa.tabata.preferences.PreferenceKeys;
import com.sisa.tabata.preferences.PreferencesSource;
import com.sisa.tabata.ui.domain.SerializedWorkout;
import com.sisa.tabata.ui.progressbar.CurrentRoundProgressBar;
import com.sisa.tabata.ui.progressbar.TotalWorkoutProgressBar;

import android.widget.ImageButton;

/**
 * Factory for {@link CountDownTimerInitializingContext}.
 *
 * @author Laszlo Sisa
 */
@Singleton
public class CountDownTimerInitializingContextFactory {

    private static final int COUNT_DOWN_HIGH_REFRESH_RATE_INTERVAL_MILLIS = 50;
    private static final int COUNT_DOWN_LOW_REFRESH_RATE_INTERVAL_MILLIS = 1000;

    @Inject
    private PreferencesSource preferencesSource;

    /**
     * Creates a configured {@link CountDownTimerInitializingContext} instance.
     *
     * @param serializedWorkout       {@link SerializedWorkout}
     * @param currentRoundProgressBar {@link CurrentRoundProgressBar}
     * @param totalWorkoutProgressBar {@link TotalWorkoutProgressBar}
     * @param playButton              {@link ImageButton}
     * @return {@link CountDownTimerInitializingContext}
     */
    public CountDownTimerInitializingContext creteContext(SerializedWorkout serializedWorkout, CurrentRoundProgressBar currentRoundProgressBar,
                                                          TotalWorkoutProgressBar totalWorkoutProgressBar, ImageButton playButton) {
        return new CountDownTimerInitializingContext.Builder()
                .withCurrentRoundProgressBar(currentRoundProgressBar)
                .withPlayButton(playButton)
                .withRefreshRateInMilis(getRefreshRate())
                .withSerializedWorkout(serializedWorkout)
                .withTotalWorkoutProgressBar(totalWorkoutProgressBar)
                .build();

    }

    private int getRefreshRate() {
        return isLowRefreshRate() ? COUNT_DOWN_LOW_REFRESH_RATE_INTERVAL_MILLIS : COUNT_DOWN_HIGH_REFRESH_RATE_INTERVAL_MILLIS;
    }

    private boolean isLowRefreshRate() {
        return preferencesSource.is(PreferenceKeys.WORKOUT_LOW_REFRESH_RATE);
    }
}
