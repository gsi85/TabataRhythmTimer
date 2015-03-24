package com.sisa.tabata.factory;

import com.google.inject.Singleton;
import com.sisa.tabata.domain.Workout;
import com.sisa.tabata.domain.WorkoutSection;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * Created by Laca on 2015.03.17..
 */
@Singleton
public class WorkoutFactory {

    private static final int NEW_WORKOUT_ID = -1;

    public Workout createWithTimeUnit(TimeUnit timeUnit) {
        Workout workout = new Workout();
        workout.setWorkoutSections(new ArrayList<WorkoutSection>());
        workout.setTimeUnit(timeUnit);
        workout.setId(NEW_WORKOUT_ID);
        return workout;
    }

}
