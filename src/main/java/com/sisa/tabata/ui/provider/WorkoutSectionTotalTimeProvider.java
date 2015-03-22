package com.sisa.tabata.ui.provider;

import com.google.inject.Singleton;
import com.sisa.tabata.domain.WorkoutSection;
import com.sisa.tabata.util.TimeFormatter;

/**
 * Created by Laca on 2015.03.18..
 */
@Singleton
public class WorkoutSectionTotalTimeProvider {

    public String getFormattedTotalTime(WorkoutSection workoutSection){
        return TimeFormatter.formatSecondsToHourMinuteSecond(getTotalTime(workoutSection));
    }

    public long getTotalTime(WorkoutSection workoutSection){
        long totalTime = workoutSection.getWarmUp() + workoutSection.getCoolDown();
        totalTime = totalTime + workoutSection.getRounds() * (workoutSection.getWork() + workoutSection.getRest());
        return totalTime;
    }
}
