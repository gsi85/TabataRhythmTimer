package com.sisa.tabata.ui.timer.domain;

import com.sisa.tabata.report.parse.ParseAnalyticsAdapter;
import com.sisa.tabata.ui.domain.SerializedWorkout;
import com.sisa.tabata.ui.progressbar.CurrentRoundProgressBar;
import com.sisa.tabata.ui.progressbar.TotalWorkoutProgressBar;
import com.sisa.tabata.ui.timer.WorkoutCountDownTimer;

import android.widget.ImageButton;

/**
 * Context required to initialize a {@link WorkoutCountDownTimer}.
 *
 * @author Laszlo Sisa
 */
public final class CountDownTimerInitializingContext {

    private final SerializedWorkout serializedWorkout;
    private final CurrentRoundProgressBar currentRoundProgressBar;
    private final TotalWorkoutProgressBar totalWorkoutProgressBar;
    private final ImageButton playButton;
    private final int refreshRateInMillis;
    private final ParseAnalyticsAdapter parseAnalyticsAdapter;

    private CountDownTimerInitializingContext(final Builder builder) {
        serializedWorkout = builder.serializedWorkout;
        currentRoundProgressBar = builder.currentRoundProgressBar;
        totalWorkoutProgressBar = builder.totalWorkoutProgressBar;
        playButton = builder.playButton;
        refreshRateInMillis = builder.refreshRateInMillis;
        parseAnalyticsAdapter = builder.parseAnalyticsAdapter;
    }

    public SerializedWorkout getSerializedWorkout() {
        return serializedWorkout;
    }

    public CurrentRoundProgressBar getCurrentRoundProgressBar() {
        return currentRoundProgressBar;
    }

    public TotalWorkoutProgressBar getTotalWorkoutProgressBar() {
        return totalWorkoutProgressBar;
    }

    public ImageButton getPlayButton() {
        return playButton;
    }

    public int getRefreshRateInMillis() {
        return refreshRateInMillis;
    }

    public ParseAnalyticsAdapter getParseAnalyticsAdapter() {
        return parseAnalyticsAdapter;
    }

    /**
     * Builder for {@link CountDownTimerInitializingContext}.
     */
    public static class Builder {

        private SerializedWorkout serializedWorkout;
        private CurrentRoundProgressBar currentRoundProgressBar;
        private TotalWorkoutProgressBar totalWorkoutProgressBar;
        private ImageButton playButton;
        private int refreshRateInMillis;
        private ParseAnalyticsAdapter parseAnalyticsAdapter;

        /**
         * Set the {@code serializedWorkout}.
         *
         * @param serializedWorkout {@link SerializedWorkout}
         * @return {@link Builder}
         */
        public Builder withSerializedWorkout(final SerializedWorkout serializedWorkout) {
            this.serializedWorkout = serializedWorkout;
            return this;
        }

        /**
         * Sets the {@code currentRoundProgressBar}.
         *
         * @param currentRoundProgressBar {@link CurrentRoundProgressBar}
         * @return {@link Builder}
         */
        public Builder withCurrentRoundProgressBar(final CurrentRoundProgressBar currentRoundProgressBar) {
            this.currentRoundProgressBar = currentRoundProgressBar;
            return this;
        }

        /**
         * Sets the {@code totalWorkoutProgressBar}.
         *
         * @param totalWorkoutProgressBar {@link TotalWorkoutProgressBar}
         * @return {@link Builder}
         */
        public Builder withTotalWorkoutProgressBar(final TotalWorkoutProgressBar totalWorkoutProgressBar) {
            this.totalWorkoutProgressBar = totalWorkoutProgressBar;
            return this;
        }

        /**
         * Sets the {@code playButton}.
         *
         * @param playButton {@link ImageButton}
         * @return {@link Builder}
         */
        public Builder withPlayButton(final ImageButton playButton) {
            this.playButton = playButton;
            return this;
        }

        /**
         * Sets the {@code refreshRateInMillis}.
         *
         * @param refreshRateInMillis refresh rate in millis
         * @return {@link Builder}
         */
        public Builder withRefreshRateInMillis(final int refreshRateInMillis) {
            this.refreshRateInMillis = refreshRateInMillis;
            return this;
        }

        /**
         * Sets the {@code parseAnalyticsAdapter}.
         *
         * @param parseAnalyticsAdapter {@link ParseAnalyticsAdapter}
         * @return {@link Builder}
         */
        public Builder withParseAnalyticsAdapter(final ParseAnalyticsAdapter parseAnalyticsAdapter) {
            this.parseAnalyticsAdapter = parseAnalyticsAdapter;
            return this;
        }

        /**
         * Builds a new {@link CountDownTimerInitializingContext}.
         *
         * @return {@link CountDownTimerInitializingContext}
         */
        public CountDownTimerInitializingContext build() {
            return new CountDownTimerInitializingContext(this);
        }
    }

}
