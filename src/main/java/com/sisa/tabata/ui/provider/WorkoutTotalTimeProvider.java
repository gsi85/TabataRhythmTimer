package com.sisa.tabata.ui.provider;

import com.google.inject.Singleton;
import com.sisa.tabata.domain.Workout;
import com.sisa.tabata.domain.WorkoutSection;
import com.sisa.tabata.util.TimeFormatter;

/**
 * Workout total time provider.
 *
 * @author Laszlo Sisa
 */
@Singleton
public class WorkoutTotalTimeProvider {

    /**
     * Provides formatted workout total time text.
     *
     * @param workout {@link Workout}
     * @return formatted total time text
     */
    public String getFormattedWorkoutTotalTime(Workout workout) {
        return TimeFormatter.formatSecondsToHourMinuteSecond(getWorkoutTotalTime(workout));
    }

    /**
     * Provides total time in seconds within a workout in numerical format.
     *
     * @param workout {@link Workout}
     * @return total time in seconds
     */
    public long getWorkoutTotalTime(Workout workout) {
        long workoutTotalTime = 0;
        for (WorkoutSection currentSection : workout.getWorkoutSections()) {
            workoutTotalTime += getSectionTotalTime(currentSection);
        }
        return workoutTotalTime;
    }

    /**
     * Provides formatted workout section total time text.
     *
     * @param workoutSection {@link WorkoutSection}
     * @return formatted total time text
     */
    public String getFormattedSectionTotalTime(WorkoutSection workoutSection) {
        return TimeFormatter.formatSecondsToHourMinuteSecond(getSectionTotalTime(workoutSection));
    }

    /**
     * Provides total time in seconds within a workout section in numerical format.
     *
     * @param workoutSection {@link WorkoutSection}
     * @return total time in seconds
     */
    public long getSectionTotalTime(WorkoutSection workoutSection) {
        long totalTime = workoutSection.getWarmUp() + workoutSection.getCoolDown();
        totalTime = totalTime + workoutSection.getRounds() * (workoutSection.getWork() + workoutSection.getRest());
        return totalTime;
    }

}
