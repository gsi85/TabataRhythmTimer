package com.sisa.tabata.factory;

import com.google.inject.Singleton;
import com.sisa.tabata.domain.WorkoutSection;

/**
 * Factory for {@link WorkoutSection}.
 *
 * @author Laszlo sisa
 */
@Singleton
public class WorkoutSectionFactory {

    private static final int MINIMUM_ROUND_COUNT = 1;

    /**
     * Creates a new {@link WorkoutSection}.
     *
     * @return {@link WorkoutSection}
     */
    public WorkoutSection create() {
        WorkoutSection workoutSection = new WorkoutSection();
        workoutSection.setRounds(MINIMUM_ROUND_COUNT);
        return workoutSection;
    }
}
