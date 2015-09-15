package com.sisa.tabata.factory;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.sisa.tabata.ApplicationContextProvider;
import com.sisa.tabata.R;
import com.sisa.tabata.domain.Workout;
import com.sisa.tabata.domain.WorkoutSection;

/**
 * Factory for {@link Workout}.
 *
 * @author Laszlo Sisa
 */
@Singleton
public class WorkoutFactory {

    private final int newWorkoutId;

    /**
     * DI constructor.
     *
     * @param applicationContextProvider {@link ApplicationContextProvider}
     */
    @Inject
    public WorkoutFactory(final ApplicationContextProvider applicationContextProvider) {
        newWorkoutId = applicationContextProvider.getIntResource(R.integer.new_workout_id);
    }

    /**
     * Creates a new {@link Workout} instance with the specified {@link TimeUnit} and initialized empty list of {@link WorkoutSection}.
     *
     * @param timeUnit {@link TimeUnit}
     * @return {@link Workout}
     */
    public Workout createWithTimeUnit(final TimeUnit timeUnit) {
        Workout workout = new Workout();
        workout.setWorkoutSections(new ArrayList<WorkoutSection>());
        workout.setTimeUnit(timeUnit);
        workout.setId(newWorkoutId);
        return workout;
    }

}
