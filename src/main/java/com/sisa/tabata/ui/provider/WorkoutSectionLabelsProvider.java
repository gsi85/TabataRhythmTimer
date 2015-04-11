package com.sisa.tabata.ui.provider;

import com.google.inject.Inject;
import com.sisa.tabata.R;
import com.sisa.tabata.TabataApplication;
import com.sisa.tabata.domain.WorkoutSection;
import com.sisa.tabata.util.TimeFormatter;
import java.util.HashMap;
import java.util.Map;
import roboguice.RoboGuice;
import roboguice.inject.ContextSingleton;

/**
 * Created by Laca on 2015.03.18..
 */
@ContextSingleton
public class WorkoutSectionLabelsProvider {

    @Inject
    private WorkoutTotalTimeProvider workoutTotalTimeProvider;

    public Map<Integer, String> getTextViewLabels(WorkoutSection workoutSection){
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
