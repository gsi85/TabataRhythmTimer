package com.sisa.tabata.ui.provider;

import com.google.inject.Singleton;
import com.sisa.tabata.domain.Workout;
import com.sisa.tabata.domain.WorkoutSection;
import com.sisa.tabata.util.TimeFormatter;

/**
 * Created by Laca on 2015.03.18..
 */
@Singleton
public class WorkoutTotalTimeProvider {

    public String getFormattedWorkoutTotalTime(Workout workout) {
        return TimeFormatter.formatSecondsToHourMinuteSecond(getWorkoutTotalTime(workout));
    }

    public long getWorkoutTotalTime(Workout workout) {
        long workoutTotalTime = 0;
        for (WorkoutSection currentSection : workout.getWorkoutSections()) {
            workoutTotalTime += getSectionTotalTime(currentSection);
        }
        return workoutTotalTime;
    }

    public String getFormattedSectionTotalTime(WorkoutSection workoutSection) {
        return TimeFormatter.formatSecondsToHourMinuteSecond(getSectionTotalTime(workoutSection));
    }

    public long getSectionTotalTime(WorkoutSection workoutSection) {
        long totalTime = workoutSection.getWarmUp() + workoutSection.getCoolDown();
        totalTime = totalTime + workoutSection.getRounds() * (workoutSection.getWork() + workoutSection.getRest());
        return totalTime;
    }
}
