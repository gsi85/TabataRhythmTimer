package com.sisa.tabata.ui.progressbar.transformer;

import com.google.inject.Inject;
import com.sisa.tabata.ApplicationContextProvider;
import com.sisa.tabata.R;
import com.sisa.tabata.ui.domain.Size;

import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.widget.TextView;

import roboguice.inject.ContextSingleton;
import roboguice.inject.InjectView;

/**
 * Transforms current round progress bar's text views sizes.
 *
 * @author Laszlo Sisa
 */
@ContextSingleton
public class RoundProgressBarTextSizeTransformer {

    private static final int UNIT_PIXEL = TypedValue.COMPLEX_UNIT_PX;
    private static final float LOW_RESOLUTION_DENSITY = 0.75F;
    private static final float EPSILON = 0.0001F;
    private static final double HIGH_RES_BLOCK_COUNTER_RATIO = 0.15;
    private static final double LOW_RES_BLOCK_COUNTER_RATIO = 0.12;
    private static final double HIGH_RES_ROUND_COUNTER_RATIO = 0.075;
    private static final double LOW_RES_ROUND_COUNTER_RATIO = 0.060;
    private static final double HIGH_RES_WORKOUT_TYPE_RATIO = 0.13;
    private static final double LOW_RES_WORKOUT_TYPE_RATIO = 0.11;

    @InjectView(R.id.currentBlockCounter)
    private TextView currentBlockCounter;
    @InjectView(R.id.roundCounter)
    private TextView roundCounter;
    @InjectView(R.id.workoutTypeText)
    private TextView workoutTypeText;
    @Inject
    private ApplicationContextProvider applicationContextProvider;

    /**
     * Sets current round progress bar text views sizes, relative to container layer physical with (px).
     *
     * @param containerSize the {@link Size} of the container in pixels
     */
    public void setTextViewSizes(final Size containerSize) {
        float density = getDensity();
        setCurrentBlockCounterSize(containerSize, density);
        setRoundCounterSize(containerSize, density);
        setWorkoutTypeText(containerSize, density);
    }

    private float getDensity() {
        DisplayMetrics displayMetrics = applicationContextProvider.getContext().getResources().getDisplayMetrics();
        return displayMetrics.density;
    }

    private void setCurrentBlockCounterSize(final Size containerSize, final float density) {
        float textSize = (float) (containerSize.getWidth() * getRatio(density, HIGH_RES_BLOCK_COUNTER_RATIO, LOW_RES_BLOCK_COUNTER_RATIO));
        currentBlockCounter.setTextSize(UNIT_PIXEL, textSize);
    }

    private void setRoundCounterSize(final Size containerSize, final float density) {
        float textSize = (float) (containerSize.getWidth() * getRatio(density, HIGH_RES_ROUND_COUNTER_RATIO, LOW_RES_ROUND_COUNTER_RATIO));
        roundCounter.setTextSize(UNIT_PIXEL, textSize);
    }

    private void setWorkoutTypeText(final Size containerSize, final float density) {
        float textSize = (float) (containerSize.getWidth() * getRatio(density, HIGH_RES_WORKOUT_TYPE_RATIO, LOW_RES_WORKOUT_TYPE_RATIO));
        workoutTypeText.setTextSize(UNIT_PIXEL, textSize);
    }

    private double getRatio(final float density, final double highResolutionRatio, final double lowResolutionRatio) {
        return isLowResolutionDensity(density) ? lowResolutionRatio : highResolutionRatio;
    }

    private boolean isLowResolutionDensity(final float density) {
        return Math.abs(density - LOW_RESOLUTION_DENSITY) < EPSILON;
    }
}
