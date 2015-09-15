package com.sisa.tabata.ui.provider;

import java.util.HashMap;
import java.util.Map;

import com.google.inject.Inject;
import com.sisa.tabata.R;
import com.sisa.tabata.domain.WorkoutSection;
import com.sisa.tabata.util.TimeFormatter;

import roboguice.inject.ContextSingleton;

/**
 * Workout section label provider.
 *
 * @author Laszlo Sisa
 */
@ContextSingleton
public class WorkoutSectionLabelsProvider {

    @Inject
    private WorkoutTotalTimeProvider workoutTotalTimeProvider;

    /**
     * Provides workout section text view labels.
     *
     * @param workoutSection {@link WorkoutSection}
     * @return map of workout section text views
     */
    public Map<Integer, String> getTextViewLabels(WorkoutSection workoutSection) {
        Map<Integer, String> textViewLabels = new HashMap<>();
        textViewLabels.put(R.id.totalTimeValue, workoutTotalTimeProvider.getFormattedSectionTotalTime(workoutSection));
        textViewLabels.put(R.id.warmUpValue, TimeFormatter.formatSecondsToMinuteSecond(workoutSection.getWarmUp()));
        textViewLabels.put(R.id.roundCountValue, Integer.toString(workoutSection.getRounds()));
        textViewLabels.put(R.id.workValue, TimeFormatter.formatSecondsToMinuteSecond(workoutSection.getWork()));
        textViewLabels.put(R.id.restValue, TimeFormatter.formatSecondsToMinuteSecond(workoutSection.getRest()));
        textViewLabels.put(R.id.coolDownValue, TimeFormatter.formatSecondsToMinuteSecond(workoutSection.getCoolDown()));
        return textViewLabels;
    }

}
